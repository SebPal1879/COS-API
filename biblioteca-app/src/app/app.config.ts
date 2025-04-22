import { ApplicationConfig, provideZoneChangeDetection, inject } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { provideApollo } from 'apollo-angular';
import { HttpLink } from 'apollo-angular/http';
import { InMemoryCache } from '@apollo/client/core';
import { getMainDefinition } from '@apollo/client/utilities';
import { WebSocketLink } from '@apollo/client/link/ws';
import { Operation } from '@apollo/client';
import { split } from '@apollo/client/core';

export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes),
    provideHttpClient(withInterceptors([])), provideHttpClient(),
    provideApollo(() => {
      const httpLink = inject(HttpLink).create({
        uri: 'http://localhost:8080/graphql',
      });

      const wsLink = new WebSocketLink({
        uri: 'ws://localhost:8080/graphql', // <- importante el ws://
        options: {
          reconnect: true,
        },
      });

      const link = split(
        ({ query }) => {
          const definition = getMainDefinition(query);
          return (
            definition.kind === 'OperationDefinition' &&
            definition.operation === 'subscription'
          );
        },
        wsLink,
        httpLink
      );

      return {
        link,
        cache: new InMemoryCache(),
      };
    })
    /*provideApollo(() => {
      const httpLink = inject(HttpLink);

      return {
        link: httpLink.create({
          uri: 'http://localhost:8080/graphql',
        }),
        cache: new InMemoryCache(),
      };
    })*/
  ]
};
