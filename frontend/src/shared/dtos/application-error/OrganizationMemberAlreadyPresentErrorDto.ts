import { ApplicationErrorCode } from '@shared/enums/error-code';
import { HttpStatus } from '@shared/enums/http';
import { ApplicationErrorDto } from './ApplicationErrorDto';

export interface OrganizationMemberAlreadyPresentErrorDto
  extends ApplicationErrorDto<
    ApplicationErrorCode.ORGANIZATION_MEMBER_ALREADY_PRESENT,
    HttpStatus.CONFLICT
  > {}
