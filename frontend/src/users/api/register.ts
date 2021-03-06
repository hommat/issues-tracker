import axios, { AxiosResponse } from 'axios';
import { RegisterUserDto } from '@users/dtos';

export const register = (dto: RegisterUserDto): Promise<AxiosResponse<string, RegisterUserDto>> =>
  axios.post(`/api/v1/user-management/users`, dto);
