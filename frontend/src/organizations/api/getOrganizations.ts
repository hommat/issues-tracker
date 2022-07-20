import axios, { AxiosResponse } from 'axios';
import { AuthorizationHeaders } from '@shared/interfaces/api-headers';
import { UserOrganizationDto } from '@organizations/dtos';

export const getOrganizations = (
  authorizationHeaders: AuthorizationHeaders
): Promise<AxiosResponse<UserOrganizationDto[], unknown>> =>
  axios.get('/api/v1/organization-management/organizations', authorizationHeaders);
