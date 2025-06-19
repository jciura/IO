import {BrowserRouter, Route, Routes} from 'react-router-dom'
import LoginForm from "./LoginForm";
import Layout from "./Layout";
import ClassesView from "./classes/ClassesView";
import AdminPanel from "./admin-panel/AdminPanel";
import RequestList from "./classes/RequestList";
import CoursePanel from "./admin-panel/CoursePanel";
import SoftwarePanel from "./admin-panel/SoftwarePanel";

function App() {
  return (
      <BrowserRouter>
          <Routes>
              <Route path="/" element={<Layout  />}>
                  <Route index path="/login" element={<LoginForm />} />
                  <Route path="/classes" element={<ClassesView />} />
                  <Route path="/confirmations" element={<RequestList />} />
                  <Route path="/admin-panel" element={<AdminPanel />} />
                  <Route path="/course-panel" element={<CoursePanel/>}/>
                  <Route path="/software-panel" element={<SoftwarePanel/>}/>
              </Route>
          </Routes>
      </BrowserRouter>
  );
}

export default App;
