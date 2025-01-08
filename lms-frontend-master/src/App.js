import { Routes, Route } from "react-router-dom";
import Header from "./NavbarComponent/Header";
import AdminRegisterForm from "./UserComponent/AdminRegisterForm";
import UserLoginForm from "./UserComponent/UserLoginForm";
import UserRegister from "./UserComponent/UserRegister";
import HomePage from "./PageComponent/HomePage";
import AddCategoryForm from "./CategoryComponent/AddCategoryForm";
import ViewAllCategories from "./CategoryComponent/ViewAllCategories";
import UpdateCategoryForm from "./CategoryComponent/UpdateCategoryForm";
import ViewAllCustomers from "./UserComponent/ViewAllCustomers";
import ViewAllMentor from "./UserComponent/ViewAllMentor";
import CourseDetailPage from "./CourseComponent/CourseDetailPage";
import PurchasedCourseDetail from "./CourseComponent/PurchasedCourseDetail";
import MentorAllCoursesPage from "./CourseComponent/MentorAllCoursesPage";
import AddCourseForm from "./CourseComponent/AddCourseForm";
import AddCourseSectionForm from "./CourseComponent/AddCourseSectionForm";
import ViewMentorCourses from "./CourseComponent/ViewMentorCourses";
import CourseBookingPage from "./CourseBookingComponent/CourseBookingPage";
import ViewCustomerCourse from "./CourseBookingComponent/ViewCustomerCourse";
import ViewMentorCoursePurchases from "./CourseBookingComponent/ViewMentorCoursePurchases";
import ViewAllCoursePurchases from "./CourseBookingComponent/ViewAllCoursePurchases";
import MentorDashboard from "./CourseComponent/MentorDashboard";
import MentorProfile from "./UserComponent/MentorProfile";
import AddProfile from "./UserComponent/AddProfile";
import LoginScreen from "./Components/LoginScreen";
import RegistrationForm from "./Components/RegistrationForm";
import CodeEditor from "./Components/Code-editor";
import BatchManager from "./Components/BatchManager";
import CreateOneCompilerUser from "./Components/CreateOneCompilerUser";
import StartChallenge from "./Components/StartChallenge";
import ChallengePage from "./Components/ChallengePage";
import Dashboard from "./Components/Dashboard";
import DashboardContent from "./Components/DashboardContent";
import StudentManager from "./Components/StudentManager";

function App() {
  return (
    <div>
      {/* 
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/loginScreen" element={<LoginScreen />} />
        <Route path="/reg" element={<RegistrationForm />} />
        <Route path="/code" element={<CodeEditor />} />
      
        <Route path="/onec" element={<CreateOneCompilerUser />} />
        <Route path="/ones" element={<StartChallenge />} />
        <Route path="/chp" element={<ChallengePage />} />
        <Route path="/home" element={<HomePage />} />
        <Route path="/user/admin/register" element={<AdminRegisterForm />} />
        <Route path="/user/login" element={<UserLoginForm />} />
        <Route path="/user/customer/register" element={<UserRegister />} />
        <Route path="/user/mentor/register" element={<UserRegister />} />
        <Route
          path="/admin/course/category/add"
          element={<AddCategoryForm />}
        />
        <Route
          path="/admin/course/category/all"
          element={<ViewAllCategories />}
        />
        <Route
          path="/admin/course/category/update"
          element={<UpdateCategoryForm />}
        />
        <Route path="/admin/customer/all" element={<ViewAllCustomers />} />

        <Route path="/admin/mentor/all" element={<ViewAllMentor />} />
        <Route path="/course/:courseId/detail" element={<CourseDetailPage />} />
        <Route
          path="/course/:courseId/videos"
          element={<PurchasedCourseDetail />}
        />
        <Route
          path="/mentor/:mentorId/course/all"
          element={<MentorAllCoursesPage />}
        />
        <Route path="/mentor/course/add" element={<AddCourseForm />} />
        <Route
          path="/mentor/course/section/add"
          element={<AddCourseSectionForm />}
        />
        <Route path="/mentor/course/all" element={<ViewMentorCourses />} />
        <Route
          path="/course/:courseId/payment/page"
          element={<CourseBookingPage />}
        />
        <Route
          path="/customer/course/purchases"
          element={<ViewCustomerCourse />}
        />
        <Route
          path="/mentor/course/customer/purchases"
          element={<ViewMentorCoursePurchases />}
        />
        <Route
          path="/admin/mentor/course/purchases/all"
          element={<ViewAllCoursePurchases />}
        />
        <Route path="/mentor/dashboard" element={<MentorDashboard />} />

        <Route path="/mentor/profile" element={<MentorProfile />} />
        <Route path="/mentor/profile/add" element={<AddProfile />} />
      
        <Route path="/dashboard" element={<Dashboard />}>
          <Route path="batches" element={<BatchManager />} />
        </Route>
      </Routes> */}


      <Routes>
        {/* Top-level routes */}
        <Route path="/" element={<HomePage />} />
        <Route path="/login" element={<LoginScreen />} />

        {/* Dashboard Layout with Nested Routes */}
        <Route path="/dashboard/*" element={<Dashboard />}>
          <Route index element={<DashboardContent />} />
          <Route path="batches" element={<BatchManager />} />
          <Route path="students" element={<StudentManager />} />
          {/* Add more nested routes here */}
        </Route>
      </Routes>

    </div>
  );
}

export default App;
