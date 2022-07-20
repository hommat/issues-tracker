import { AxiosError, AxiosResponse } from 'axios';
import { useQuery, UseQueryResult } from 'react-query';
import { getOrganizations } from '@organizations/api';
import { UserOrganizationDto } from '@organizations/dtos';
import { GET_ORGANIZATIONS_QUERY } from '@organizations/consts/react-query-keys';
import { ApplicationErrorDto } from '@shared/dtos/application-error';
import { useAuthorizationHeaders } from '@shared/hooks/api';
import { ApplicationErrorCode } from '@shared/enums/error-code';
import { HttpStatus } from '@shared/enums/http';

export const useOrganizations = (
  onError: (error: AxiosError<ApplicationErrorDto<any, any>, unknown>) => void
): UseQueryResult<
  AxiosResponse<UserOrganizationDto[], unknown>,
  AxiosError<ApplicationErrorDto<ApplicationErrorCode, HttpStatus>, unknown>
> => {
  const authorizationHeaders = useAuthorizationHeaders();
  return useQuery(GET_ORGANIZATIONS_QUERY, () => getOrganizations(authorizationHeaders), {
    onError,
  });
};
