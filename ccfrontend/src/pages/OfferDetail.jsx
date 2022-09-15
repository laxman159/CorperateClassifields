import axios from "axios";
import { useEffect, useState } from "react";
import Helmet from "react-helmet";
import { Link, useNavigate, useParams } from "react-router-dom";
import Spinner from "../utils/Spinner";
import img1 from "../resources/img1.jpg";
import img2 from "../resources/img2.jpg";
import img3 from "../resources/img3.jpg";
import gsap from "gsap";
import { employeeBaseURL, offerBaseURL } from "../utils/UrlRoutes";

const OfferDetail = () => {
  const params = useParams();
  const offerId = params.id;
  const navigate = useNavigate();
  const [offerDetails, setofferDetails] = useState();
  const [isLoading, setLoading] = useState(true);

  const imageArray = [img1, img2, img3];

  var randomImage = imageArray[Math.floor(Math.random() * imageArray.length)];

  const userObject = window.localStorage.getItem("employee");
  const userId = JSON.parse(userObject).empid;
  const token = JSON.parse(userObject).authToken;

  useEffect(() => {
    axios
      .get(
        `${offerBaseURL}/getOffers?category=empty&date=empty&id=${offerId}&likes=false`,
        {
          headers: { Authorization: `Bearer ${token}` },
        }
      )
      .then(function (response) {
        console.log(response.data);
        setofferDetails(response.data[0]);
        setLoading(false);
      })
      .catch(function (error) {
        if (error.response.status === 500 || error.response.status === 401) {
          navigate("/login");
        }
      });
    gsap.fromTo(".img", { opacity: 0 }, { opacity: 1, duration: 5 });
  }, []);
  const date = new Date(offerDetails?.openDate);
  const engageDate = new Date(offerDetails?.engagedDate);

  const handleLike = () => {
    axios
      .post(`${employeeBaseURL}/likeOffer/${offerDetails.id}`, "", {
        headers: { Authorization: `Bearer ${token}` },
      })
      .then(function (_response) {
        setTimeout(() => {
          window.location.reload();
        }, 2000);
      });
  };

  const handleEngage = () => {
    axios
      .post(
        `${offerBaseURL}/engageOffer?employeeId=${userId}&offerId=${offerDetails.id}`,
        "",
        {
          headers: { Authorization: `Bearer ${token}` },
        }
      )
      .then(function (_response) {
        window.location.reload();
      });
  };

  return isLoading ? (
    <div className='w-full flex items-center justify-center mt-52 '>
      <Spinner />
    </div>
  ) : (
    <div className='flex flex-col items-center h-full  w-full'>
      <Helmet>
        <title>Offer Details | Corperate Classifields</title>
      </Helmet>
      <div className='flex items-center justify-center flex-col sm:flex-row md:mt-10 mb-10'>
        <div className='md:w-1/3 mt-20 sm:w-full'>
          <img
            className='w-3/3 img rounded-full p-5 '
            src={randomImage}
            alt=''
          />
        </div>
        <div className='flex flex-col rounded justify-center w-1/2 sm:w-1/2 sm:p-10 items-center mt-3 p-5'>
          <h1 className='text-3xl font-bold tracking-wide'>
            {offerDetails.name}
          </h1>
          <h2 className='text-xl mt-5'>{offerDetails.description}</h2>
          <h1 className='mt-4 font-thin'>Category: {offerDetails.category}</h1>
          <h1>{offerDetails.closedDate}</h1>
          <h1 className='font-thin'>
            Posted on:
            {" " +
              date.getDate() +
              "/" +
              (date.getMonth() + 1) +
              "/" +
              date?.getFullYear()}
          </h1>
          <h1 className='font-thin'>
            {offerDetails.engagedDate
              ? `Offer engaged on: ${
                  "" +
                  engageDate.getDate() +
                  "/" +
                  (engageDate.getMonth() + 1) +
                  "/" +
                  engageDate?.getFullYear()
                }`
              : ""}
          </h1>
          <h1 className='mt-5'>Likes: {offerDetails.likes}</h1>
          {/* <h1>{offerDetails.empId}</h1> */}
        </div>
      </div>
      <div className='flex items-center justify-center mt-5'>
        {userId === offerDetails.empId ? (
          <button className='rightheader mr-5  cursor-pointer  bg-gray-800 text-white p-2 rounded hover:bg-gray-900 text-xl font-thin tracking-wider'>
            <Link to={`/editOffer/${offerDetails.id}`}>Edit Offer</Link>
          </button>
        ) : (
          <div className='mb-5'>
            <button
              onClick={handleLike}
              className='rightheader mr-5 cursor-pointer  bg-gray-800 text-white p-2 rounded font-thin tracking-wider hover:bg-gray-900 text-xl'
            >
              Like Offer
            </button>
            {offerDetails?.engagedDate ? (
              ""
            ) : (
              <button
                onClick={handleEngage}
                className='rightheader mr-5 cursor-pointer  bg-gray-800 text-white p-2 rounded hover:bg-gray-900 text-xl font-thin tracking-wider'
              >
                Engage Offer
              </button>
            )}
          </div>
        )}
      </div>
    </div>
  );
};

export default OfferDetail;
