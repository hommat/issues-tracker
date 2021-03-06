import { useParams as useReactRouterParams } from 'react-router-dom';

export const useParams = <TParams extends Record<string, string>>(): TParams =>
  useReactRouterParams() as TParams;
