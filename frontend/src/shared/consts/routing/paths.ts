export type Path =
  | 'shared.home'
  | 'users.register'
  | 'users.login'
  | 'users.activation'
  | 'organizations.list'
  | 'organizations.create'
  | 'organizations.projects.create'
  | 'organizations.details'
  | 'organizations.member.invite'
  | 'organizations.member.accept';
export const paths: Record<Path, string> = {
  'shared.home': '/',
  'users.login': '/login',
  'users.register': '/register',
  'users.activation': '/users/:userId/activation/:activationToken',
  'organizations.list': '/organizations',
  'organizations.create': '/organizations/create',
  'organizations.projects.create': '/organizations/:organizationId/projects/create',
  'organizations.details': '/organizations/:organizationId',
  'organizations.member.invite': '/organizations/:organizationId/invitations',
  'organizations.member.accept': '/organizations/:organizationId/members',
};
