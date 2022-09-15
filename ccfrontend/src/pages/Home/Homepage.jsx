import AllOffers from "../AllOffers";
import { useEffect, useState } from "react";
import axios from "axios";
import Select from "react-select";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import { offerBaseURL } from "../../utils/UrlRoutes";

function Homepage() {
  const [category, setCategory] = useState([]);
  const [selectedCategory, setselectedCategory] = useState(null);
  const [likedOffers, setLikedOffers] = useState(false);
  const [recentlyLiked, setrecentlyLiked] = useState(false);
  const [startDate, setStartDate] = useState(new Date());

  useEffect(() => {
    if (window.localStorage.getItem("employee")) {
      const userObject = window.localStorage.getItem("employee");
      const token = JSON.parse(userObject).authToken;

      axios
        .get(`${offerBaseURL}/getDistinctCategory`, {
          headers: { Authorization: `Bearer ${token}` },
        })
        .then(function (response) {
          var result = Object.keys(response.data).map(function (key) {
            return { value: response.data[key], label: response.data[key] };
          });
          setCategory(result);
        })
        .catch(function (_error) {});
    }
  }, []);

  // console.log(category);
  const customStyles = {
    control: (base, state) => ({
      ...base,
      background: "white",
      borderRadius: state.isFocused ? "3px 3px 0 0" : 3,
      borderColor: state.isFocused ? "green" : "black",
      "&:hover": {
        // borderColor: state.isFocused ? "red" : "blue",
      },
    }),
    menu: (base) => ({
      ...base,
      borderRadius: 0,
      marginTop: 0,
    }),
    menuList: (base) => ({
      ...base,
      padding: 0,
    }),
  };

  return (
    <div className=''>
      <div className='   flex  items-center justify-between'>
        <div className='w-4/6  flex  items-center'>
          <div className=' w-1/3 ml-5 p-5 '>
            <Select
              className=''
              styles={customStyles}
              // defaultValue={""}
              options={category}
              classNamePrefix='select'
              isClearable
              onChange={(option) =>
                selectedCategory === null
                  ? setselectedCategory(option.value)
                  : setselectedCategory(null)
              }
            />
          </div>
          <div>
            <button
              className=' ml-5 p-2 w-full cursor-pointer  bg-gray-800 text-white rounded font-semibold hover:bg-gray-900'
              onClick={() => setLikedOffers(!likedOffers)}
            >
              {likedOffers === false ? "Top liked offers" : "All offers"}
            </button>
          </div>
          <div className='p-1'>
            <button
              className=' ml-10 p-2 w-full cursor-pointer  bg-gray-800 text-white rounded font-semibold hover:bg-gray-900'
              onClick={() => setrecentlyLiked(!recentlyLiked)}
            >
              {recentlyLiked === false ? "Recently liked offers" : "All offers"}
            </button>
          </div>
        </div>

        <div>
          <DatePicker
            selected={startDate}
            onChange={(date) => setStartDate(date)}
            dateFormat='yyyy-MM-dd'
            wrapperClassName='datePicker'
            className='ml-5 border-2 border-black p-2 rounded mr-5'
          />
        </div>
      </div>
      <AllOffers
        selectedCategory={selectedCategory}
        likedOffers={likedOffers}
        startDate={startDate}
        recentlyLiked={recentlyLiked}
      />
    </div>
  );
}

export default Homepage;
