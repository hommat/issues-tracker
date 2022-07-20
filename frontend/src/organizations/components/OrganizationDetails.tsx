import { AxiosError } from 'axios';
import { Button, Flex, Heading, Spacer, Spinner, Text } from '@chakra-ui/react';
import { useState } from 'react';
import { Link } from 'react-router-dom';
import { useOrganizationDetails } from '@organizations/hooks/api';
import { OrganizationParams } from '@organizations/types';
import { ApplicationErrorDto } from '@shared/dtos/application-error';
import { ApplicationErrorCode } from '@shared/enums/error-code';
import { HttpStatus } from '@shared/enums/http';
import { applicationErrorHandler } from '@shared/helpers/application-error';
import { reverse } from '@shared/helpers/routing';

interface OrganizationDetailsProps extends OrganizationParams {}

export const OrganizationDetails: React.FC<OrganizationDetailsProps> = ({ organizationId }) => {
  const [error, setError] = useState<string>('');
  const organizationParams: OrganizationParams = { organizationId };

  const handleError = (
    error: AxiosError<ApplicationErrorDto<ApplicationErrorCode, HttpStatus>, unknown>
  ): void => {
    applicationErrorHandler()
      .onAuthInvalidJwt(({ message }) => setError(message))
      .onAuthAccessDenied(({ message }) => setError(message))
      .onOrganizationNotFound(({ message }) => setError(message))
      .handleAxiosError(error);
  };

  const { data, isLoading, isSuccess, isError } = useOrganizationDetails(organizationId, {
    onError: handleError,
  });
  return (
    <>
      {isLoading && <Spinner />}
      {isSuccess && (
        <>
          <Heading size="xl">{data?.data.name}</Heading>
          <Flex width="100%">
            <Flex direction="column">
              <Heading size="lg">Projects</Heading>
              {data?.data.projects.map((project) => (
                <Text key={project.id}>{project.name}</Text>
              ))}
              <Link
                to={reverse({ path: 'organizations.projects.create', params: organizationParams })}
              >
                <Button>Add project</Button>
              </Link>
            </Flex>
            <Spacer />
            <Flex direction="column">
              <Heading size="lg">Members</Heading>
              {data?.data.members.map((member) => (
                <Text key={member.id}>{member.id}</Text>
              ))}
              <Button>Add member</Button>
            </Flex>
          </Flex>
        </>
      )}
      {isError && <Text>{error}</Text>}
    </>
  );
};
