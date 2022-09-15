import axios from "axios";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import OfferComponent from "../components/OfferComponent";
import Spinner from "../utils/Spinner";
import { employeeBaseURL, offerBaseURL } from "../utils/UrlRoutes";

const AllOffers = ({
  selectedCategory,
  likedOffers,
  startDate,
  recentlyLiked,
}) => {
  const navigate = useNavigate();
  const [offers, setoffers] = useState([]);
  const [isLoading, setLoading] = useState(true);

  useEffect(() => {
    if (window.localStorage.getItem("employee")) {
      const userObject = window.localStorage.getItem("employee");
      const token = JSON.parse(userObject).authToken;

      if (
        selectedCategory !== null &&
        likedOffers === false &&
        startDate.getDate() === new Date().getDate() &&
        recentlyLiked === false
      ) {
        axios
          .get(
            `${offerBaseURL}/getOffers?category=${selectedCategory}&id=0&likes=false`,
            {
              headers: { Authorization: `Bearer ${token}` },
            }
          )
          .then(function (response) {
            console.log(response.data);
            setoffers(response.data);
            setLoading(false);
          })
          .catch(function (error) {
            console.log(error.response.status);
            if (
              error.response.status === 500 ||
              error.response.status === 401
            ) {
              navigate("/login");
            }
          });
      } else if (
        selectedCategory === null &&
        likedOffers === true &&
        startDate.getDate() === new Date().getDate() &&
        recentlyLiked === false
      ) {
        axios
          .get(`${offerBaseURL}/getOffers?category=empty&id=0&likes=true`, {
            headers: { Authorization: `Bearer ${token}` },
          })
          .then(function (response) {
            // console.log(response.data);
            setoffers(response.data);
            setLoading(false);
          })
          .catch(function (error) {
            console.log(error.response.status);
            if (
              error.response.status === 500 ||
              error.response.status === 401
            ) {
              navigate("/login");
            }
          });
      } else if (
        selectedCategory === null &&
        likedOffers === false &&
        startDate.getDate() !== new Date().getDate() &&
        recentlyLiked === false
      ) {
        const date1 = startDate.getDate();
        let month = startDate.getUTCMonth() + 1;
        var dateString =
          startDate.getFullYear() +
          "-" +
          ("0" + (startDate.getMonth() + 1)).slice(-2) +
          "-" +
          ("0" + startDate.getDate()).slice(-2);

        const year = startDate.getFullYear();
        const finalDate = year + "-" + month + "-" + date1;

        axios
          .get(
            `${offerBaseURL}/getOffers?category=empty&date=${dateString}&id=0&likes=false`,
            {
              headers: { Authorization: `Bearer ${token}` },
            }
          )
          .then(function (response) {
            console.log(response.data);

            setoffers(response.data);
            setLoading(false);
          })
          .catch(function (error) {
            console.log(error.response.status);
            if (
              error.response.status === 500 ||
              error.response.status === 401
            ) {
              navigate("/login");
            }
          });
      } else if (
        selectedCategory === null &&
        likedOffers === false &&
        startDate.getDate() === new Date().getDate() &&
        recentlyLiked === true
      ) {
        axios
          .get(`${employeeBaseURL}/recentlyLiked`, {
            headers: { Authorization: `Bearer ${token}` },
          })
          .then(function (response) {
            // console.log(response.data);
            setoffers(response.data);
            setLoading(false);
          })
          .catch(function (error) {
            console.log(error.response.status);
            if (
              error.response.status === 500 ||
              error.response.status === 401
            ) {
              navigate("/login");
            }
          });
      } else {
        axios
          .get(`${offerBaseURL}/getOffers?category=empty&id=0&likes=false`, {
            headers: { Authorization: `Bearer ${token}` },
          })
          .then(function (response) {
            console.log(response.data);
            setoffers(response.data);
            setLoading(false);
          })
          .catch(function (error) {
            // console.log(error.response.status);
            if (
              error.response.status === 500 ||
              error.response.status === 401
            ) {
              navigate("/login");
            }
          });
      }
    } else {
      window.localStorage.clear();
      navigate("/login");
    }
  }, [selectedCategory, likedOffers, startDate, recentlyLiked]);

  return isLoading ? (
    <div className=' w-full flex items-center justify-center mt-52 '>
      <Spinner />
    </div>
  ) : (
    <div className=' w-auto  flex flex-wrap justify-center '>
      {offers.map((offer) => (
        <OfferComponent key={offer.id} offer={offer} />
      ))}
    </div>
  );
};

export default AllOffers;
