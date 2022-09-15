import axios from "axios";
import gsap from "gsap";
import { useEffect, useState } from "react";
import Helmet from "react-helmet";
import { useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";
import loginIng from "../../resources/loginImg.jpg";
import { authBaseURL } from "../../utils/UrlRoutes";
function LoginPage({ setisLoggedIn }) {
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm();

  const onSubmit = (data) => {
    console.log(data);
    setisLoggedIn(true);
    logInUser(data, setisLoggedIn);
  };

  const [error1, seterror1] = useState("");

  const navigate = useNavigate();

  useEffect(() => {
    // if (window.localStorage.getItem("employee")) {
    //   navigate("/");
    // }
    gsap.fromTo(".canvas", { opacity: 0 }, { opacity: 1, duration: 1 });
    gsap.fromTo(
      ".input",
      { width: 0 },
      { width: "auto", duration: 1, stagger: 0.3 }
    );
    gsap.fromTo(".logbtn", { width: 0 }, { width: "50%", duration: 1 });
  }, []);

  const logInUser = (data, setisLoggedIn) => {
    seterror1("");
    window.localStorage.removeItem("employee");
    axios
      .post(`${authBaseURL}/login`, data)
      .then(function (response) {
        console.log(response.data);
        window.localStorage.setItem("employee", JSON.stringify(response.data));
        navigate("/");
      })

      .catch(function (error) {
        console.log(error?.response?.status);
        seterror1(error?.response?.status);
      });
  };
  return (
    <div className='main h-full flex flex-col items-center justify-center '>
      <Helmet>
        <title>Login | Corperate Classifields</title>
      </Helmet>
      <div className='canvas  w-9/12 flex items-center justify-center shadow-2xl m-10 rounded  h-auto'>
        <div className='left w-1/2    '>
          <img className='w-auto h-auto md:p-20' src={loginIng} alt='' />
        </div>
        <div className='right flex  w-1/2 h-auto  p-3'>
          <div className='flex w-full items-center justify-center   md:mt-10'>
            <form onSubmit={handleSubmit(onSubmit)} className=''>
              <div className='mb-8 -mt-10  '>
                <h1 className='text-5xl font-semibold '>Welcome back.</h1>
              </div>
              <div className='flex  flex-col'>
                <div className='mb-8 flex flex-col'>
                  <input
                    className='input p-2 appearance-none block sm:w-full md:w-full bg-gray-200 placeholder-gray-900 rounded border focus:border-teal-500 '
                    type='text'
                    {...register("empUsername", { required: true })}
                    placeholder='Your Name'
                  />
                  {errors.empUsername && (
                    <span className='text-red-600 ml-auto mr-auto'>
                      Username is required
                    </span>
                  )}
                </div>
                <div className='mb-8 flex flex-col'>
                  <input
                    className='input p-2 appearance-none block sm:w-full md:w-3/3 bg-gray-200 placeholder-gray-900 rounded border focus:border-teal-500'
                    type='password'
                    {...register("empPassword", { required: true })}
                    placeholder='Password'
                  />
                  {errors.empPassword && (
                    <span className='text-red-600 ml-auto mr-auto'>
                      Password is required
                    </span>
                  )}
                  {error1 && (
                    <span className='text-red-600 ml-auto mr-auto'>
                      Invalid Credentials
                    </span>
                  )}
                </div>
                <input
                  className='logbtn mr-auto w-1/2 bg-gray-800 text-white p-2 rounded font-semibold hover:bg-gray-900'
                  type='submit'
                  value='Log In'
                />
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
}

export default LoginPage;
