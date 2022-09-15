import axios from "axios";
import React, { useEffect, useState } from "react";
import Helmet from "react-helmet";
import { useNavigate } from "react-router-dom";
import OfferComponent from "../components/OfferComponent";
import user from "../resources/user.jpg";
import { employeeBaseURL, pointBaseURL } from "../utils/UrlRoutes";

const MyProfile = () => {
  const [profile, setprofile] = useState([]);
  const [pointsGained, setpointsGained] = useState(0);
  const [mostLiked, setmostLiked] = useState(false);
  const [mostLikedUserOffer, setmostLikedUserOffer] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    const userObject = window.localStorage.getItem("employee");
    const token = JSON.parse(userObject).authToken;
    const userId = JSON.parse(userObject).empid;
    axios
      .get(`${employeeBaseURL}/viewProfile/${userId}`, {
        headers: { Authorization: `Bearer ${token}` },
      })
      .then(function (response) {
        console.log(response.data);
        setprofile(response.data);
      })
      .catch(function (error) {
        console.log(error.response.status);
        if (error.response.status) {
          navigate("/");
        }
      });
    axios
      .get(`${pointBaseURL}/getpointsbyemp/${userId}`, {
        headers: { Authorization: `Bearer ${token}` },
      })
      .then(function (response) {
        setpointsGained(response.data);
      });
  }, []);

  const handleRefreshPoints = () => {
    const userObject = window.localStorage.getItem("employee");
    const token = JSON.parse(userObject).authToken;
    const userId = JSON.parse(userObject).empid;
    axios
      .post(`${pointBaseURL}/refreshpointsbyemp/${userId}`, "", {
        headers: { Authorization: `Bearer ${token}` },
      })
      .then(function (_response) {
        window.location.reload();
      });
  };
  const displayMostLikedOffer = () => {
    const userObject = window.localStorage.getItem("employee");
    const token = JSON.parse(userObject).authToken;
    const userId = JSON.parse(userObject).empid;
    setmostLiked(!mostLiked);
    axios
      .get(`${employeeBaseURL}/viewMostLikedOffers/${userId}`, {
        headers: { Authorization: `Bearer ${token}` },
      })
      .then(function (response) {
        setmostLikedUserOffer(response.data);
      })
      .catch(function (error) {
        console.log(error.response.status);
        if (error.response.status === 500 || error.response.status === 400) {
          navigate("/");
        }
      });
  };

  return (
    <div className=''>
      <Helmet>
        <title>Profile | Corperate Classifields</title>
      </Helmet>
      <div className='flex sm:flex-col md:flex-row sm:items-center sm:justify-center md:justify-start md:items-start  mt-10 '>
        <div className='w-2/4 flex flex-col items-center justify-start mt-5 '>
          <img src={user} alt='' className='rounded-full w-1/3' />
          <div className='mt-5 p-5 w-1/2 font-thin text-xl flex items-start justify-center flex-col'>
            <h1>Name: {profile?.name?.toUpperCase()}</h1>
            <h1>Age: {profile?.age}</h1>
            <h1>Gender: {profile?.gender?.toUpperCase()}</h1>
            <h3>
              Department:{" "}
              {profile.department
                ? profile.department.toUpperCase()
                : "Lorem Ipsum".toUpperCase()}
            </h3>
            <span className='mt-2'>
              {profile.email
                ? profile.email
                : "user123@corperateclassifield.com"}
            </span>
          </div>
          <div className='flex h-full  items-center justify-between p-5 '>
            <h1 className='font-thin tracking-wide md:text-2xl mt-5 mr-5'>
              Points gained:{" "}
              <span className='text-green-500 font-bold'>
                {profile.pointsGained}
              </span>
            </h1>
          </div>
          <div>
            <button
              onClick={handleRefreshPoints}
              className=' mt-4 ml-5 cursor-pointer  bg-gray-800 text-white p-2 rounded font-semibold hover:bg-gray-900'
            >
              Refresh points
            </button>
            <button
              className=' mr-5 ml-5 cursor-pointer  bg-gray-800 text-white p-2 rounded font-semibold hover:bg-gray-900'
              onClick={displayMostLikedOffer}
            >
              {mostLiked === false ? "Most Liked Offers" : "Your Offers"}
            </button>
          </div>
        </div>
        <hr />
        <div className='flex flex-wrap  md:w-3/4 '>
          <h1 className=' text-3xl  tracking-wide flex items-center justify-center w-full text-green-600 font-sans'>
            {mostLiked ? "Most Liked Offers" : "Your Offers"}
          </h1>
          <div className=' flex flex-row  flex-wrap items-center justify-center'>
            {mostLiked
              ? mostLikedUserOffer.map((offer) => (
                  <OfferComponent offer={offer} key={offer.id} />
                ))
              : profile?.offers?.map((offer) => (
                  <OfferComponent offer={offer} key={offer.id} />
                ))}
          </div>
        </div>
      </div>
    </div>
  );
};

export default MyProfile;
