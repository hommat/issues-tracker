import { ChakraProvider } from '@chakra-ui/react';
import { Route, Routes } from 'react-router-dom';
import { SecureRoute } from '@shared/components';
import { paths } from '@shared/consts/routing';
import { HomePage } from '@shared/pages';
import { CustomQueryClientProvider } from '@shared/providers/query';
import { UserProvider } from '@users/contexts';
import { LoginPage, RegisterPage, UserActivationPage } from '@users/pages';
import {
  CreateOrganizationPage,
  CreateProjectPage,
  OrganizationDetailsPage,
  OrganizationsPage,
  MemberInvitationPage,
  MemberAcceptancePage,
} from '@organizations/pages';
import { SseProvider } from '@notifications/contexts';

export const App: React.FC = () => {
  return (
    <UserProvider>
      <SseProvider>
        <CustomQueryClientProvider>
          <ChakraProvider>
            <Routes>
              <Route path={paths['shared.home']} element={<HomePage />} />
              <Route path={paths['shared.home']} element={<SecureRoute userRequired={false} />}>
                <Route path={paths['users.login']} element={<LoginPage />} />
                <Route path={paths['users.register']} element={<RegisterPage />} />
              </Route>
              <Route path={paths['users.activation']} element={<UserActivationPage />} />
              <Route path={paths['shared.home']} element={<SecureRoute userRequired={true} />}>
                <Route path={paths['organizations.list']} element={<OrganizationsPage />} />
                <Route path={paths['organizations.create']} element={<CreateOrganizationPage />} />
                <Route
                  path={paths['organizations.projects.create']}
                  element={<CreateProjectPage />}
                />
                <Route
                  path={paths['organizations.details']}
                  element={<OrganizationDetailsPage />}
                />
                <Route
                  path={paths['organizations.member.invite']}
                  element={<MemberInvitationPage />}
                />
                <Route
                  path={paths['organizations.member.accept']}
                  element={<MemberAcceptancePage />}
                />
              </Route>
            </Routes>
          </ChakraProvider>
        </CustomQueryClientProvider>
      </SseProvider>
    </UserProvider>
  );
};
