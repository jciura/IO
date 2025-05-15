import { BrowserRouter, Route, Routes } from 'react-router-dom'
import LoginForm from "./LoginForm";
import Layout from "./Layout";
import ClassesView from "./classes/ClassesView";
import AdminPanel from "./admin-panel/AdminPanel";

function App() {
  return (
      <BrowserRouter>
          <Routes>
              <Route path="/" element={<Layout  />}>
                  <Route index path="/login" element={<LoginForm />} />
                  <Route path="/classes" element={<ClassesView />} />
                  <Route path="/admin-panel" element={<AdminPanel />} />
              </Route>
          </Routes>
      </BrowserRouter>
  );
}

export default App;
