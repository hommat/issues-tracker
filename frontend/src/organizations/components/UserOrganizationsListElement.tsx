import { Box, Button, Heading, List, ListItem } from '@chakra-ui/react';
import { Link } from 'react-router-dom';
import { UserOrganizationDto } from '@organizations/dtos';
import { OrganizationParams } from '@organizations/types';
import { reverse } from '@shared/helpers/routing';

interface UserOrganizationsListElementProps extends UserOrganizationDto {}

export const UserOrganizationsListElement: React.FC<UserOrganizationsListElementProps> = ({
  name,
  projects,
  id,
}) => {
  const organizationParams: OrganizationParams = {
    organizationId: id,
  };
  return (
    <Box>
      <Link to={reverse({ path: 'organizations.details', params: organizationParams })}>
        <Heading size="lg">{name}</Heading>
      </Link>
      <List>
        {projects.map(({ id, name }) => (
          <ListItem key={id}>{name}</ListItem>
        ))}
        <Link to={reverse({ path: 'organizations.projects.create', params: organizationParams })}>
          <Button variant="outline">Add project</Button>
        </Link>
      </List>
    </Box>
  );
};
