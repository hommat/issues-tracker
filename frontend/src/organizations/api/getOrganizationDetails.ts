import { UserOrganizationDto } from '@organizations/dtos';
import { AuthorizationHeaders } from '@shared/interfaces/api-headers';
import axios, { AxiosResponse } from 'axios';

export const getOrganizationDetails = (
  authorizationHeaders: AuthorizationHeaders,
  organizationId: string
): Promise<AxiosResponse<UserOrganizationDto, unknown>> =>
  axios.get(
    `/api/v1/organization-management/organizations/${organizationId}`,
    authorizationHeaders
  );
