import { AxiosError, AxiosResponse } from 'axios';
import { useQuery, UseQueryResult } from 'react-query';
import { getOrganizationDetails } from '@organizations/api';
import { GET_ORGANIZATION_DETAILS_QUERY } from '@organizations/consts/react-query-keys';
import { UserOrganizationDto } from '@organizations/dtos';
import { ApplicationErrorDto } from '@shared/dtos/application-error';
import { ApplicationErrorCode } from '@shared/enums/error-code';
import { HttpStatus } from '@shared/enums/http';
import { useAuthorizationHeaders } from '@shared/hooks/api';

interface UseQueryConfig {
  onError?: (
    error: AxiosError<ApplicationErrorDto<ApplicationErrorCode, HttpStatus>, unknown>
  ) => void;
  onSuccess?: (response: AxiosResponse<UserOrganizationDto, unknown>) => void;
}

export const useOrganizationDetails = (
  organizationId: string,
  useQueryConfig: UseQueryConfig
): UseQueryResult<
  AxiosResponse<UserOrganizationDto, unknown>,
  AxiosError<ApplicationErrorDto<ApplicationErrorCode, HttpStatus>, unknown>
> => {
  const authorizationHeaders = useAuthorizationHeaders();

  return useQuery(
    [GET_ORGANIZATION_DETAILS_QUERY, organizationId],
    () => getOrganizationDetails(authorizationHeaders, organizationId),
    useQueryConfig
  );
};
