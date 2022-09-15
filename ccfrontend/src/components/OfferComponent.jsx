import gsap from "gsap";
import React, { useEffect } from "react";
import Helmet from "react-helmet";
import { Link } from "react-router-dom";

const OfferComponent = ({ offer }) => {
  useEffect(() => {
    gsap.fromTo(
      ".card",
      { opacity: 0, scale: 0.8 },
      { opacity: 1, scale: 1, stagger: 0.2, duration: 1 }
    );
  }, []);

  return (
    <div className='md:w-4/12  sm:w-6/12  p-10    flex items-center justify-center'>
      <Helmet>
        <title>Offers | Corperate Classifields</title>
      </Helmet>
      <div className='card opacity-0 p-6 min-w-full min-h-full  rounded-lg border border-gray-200 shadow-md overflow-hidden dark:bg-gray-800 dark:border-gray-700 mb-2 mt-2'>
        <a href='sfsdf'>
          <h5 className='mb-2 w-full text-2xl font-bold tracking-tight text-gray-900 dark:text-white'>
            {offer.name}
          </h5>
        </a>
        <p className='mb-3  md:text-lg md:font-thin sm:text-base font-normal text-gray-700 dark:text-gray-400 h-40 overflow-hidden'>
          {offer.description}
        </p>
        <a
          href={`/details/${offer.id}`}
          className='inline-flex items-center py-2 px-3 text-sm font-medium text-center text-white bg-blue-700 rounded-lg hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800'
        >
          <Link to={`/details/${offer.id}`}>Details</Link>
          <svg
            className='ml-2 -mr-1 w-4 h-4'
            fill='currentColor'
            viewBox='0 0 20 20'
            xmlns='http://www.w3.org/2000/svg'
          >
            <path
              fillRule='evenodd'
              d='M10.293 3.293a1 1 0 011.414 0l6 6a1 1 0 010 1.414l-6 6a1 1 0 01-1.414-1.414L14.586 11H3a1 1 0 110-2h11.586l-4.293-4.293a1 1 0 010-1.414z'
              clipRule='evenodd'
            ></path>
          </svg>
        </a>
      </div>
    </div>
  );
};

export default OfferComponent;
