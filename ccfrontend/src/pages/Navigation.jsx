import gsap from "gsap";
import { useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";
import user from "../resources/user.jpg";

const Navigation = ({ isLoggedIn, setisLoggedIn }) => {
  const navigate = useNavigate();

  const username = JSON.parse(
    window.localStorage.getItem("employee")
  )?.username;

  const logout = () => {
    if (window.localStorage.getItem("employee")) {
      window.localStorage.removeItem("employee");
      setisLoggedIn(!isLoggedIn);
      navigate("/login");
    }
  };

  useEffect(() => {
    if (!window.localStorage.getItem("employee")) {
      setisLoggedIn(false);
    }
    gsap.fromTo(
      ".rightheader",
      { y: -100 },
      { y: 0, stagger: 0.2, duration: 0.5 }
    );
  }, []);

  return (
    <div className='flex flex-row items-center justify-between sm:text-sm md:text-lg text-base shadow-sm'>
      <div>
        <h1 className='rightheader text-3xl  tracking-wide mr-auto text-green-600 font-sans p-5'>
          <Link to={"/"}>Corperate Classifields</Link>
        </h1>
      </div>
      <div className=' flex p-5 items-center font-semibold'>
        <h1 className='rightheader mr-10 cursor-pointer hover:underline'>
          <Link to={"/addOffer"}>Add Offer</Link>
        </h1>
        <div className='rightheader'>
          <Link
            className='flex items-center cursor-pointer hover:underline mr-7'
            to={"/myProfile"}
          >
            <img src={user} className='w-8 rounded-full h-8 mr-2' alt='' />
            <h1>{username ? username : ""}</h1>
          </Link>
        </div>
        <button
          onClick={logout}
          className='rightheader mr-5 cursor-pointer  bg-gray-800 text-white p-2 rounded font-semibold hover:bg-gray-900'
        >
          {isLoggedIn ? "Log Out" : "Log In"}
        </button>
      </div>
    </div>
  );
};

export default Navigation;
