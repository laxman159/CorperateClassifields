import { useEffect, useState } from "react";
import { Route, Routes, useNavigate } from "react-router-dom";
import AddOffer from "./pages/AddOffer";
import EditOffer from "./pages/EditOffer";
import Homepage from "./pages/Home/Homepage";
import LoginPage from "./pages/Login/LoginPage";
import MyOffers from "./pages/MyOffers";
import MyProfile from "./pages/MyProfile";
import Navigation from "./pages/Navigation";
import NotFound from "./pages/NotFound";
import OfferDetail from "./pages/OfferDetail";

function App() {
  const [isLoggedIn, setisLoggedIn] = useState(true);

  return (
    <div className=''>
      <Navigation isLoggedIn={isLoggedIn} setisLoggedIn={setisLoggedIn} />

      <Routes>
        <Route path='/' element={<Homepage />} />
        <Route
          path='login'
          element={<LoginPage setisLoggedIn={setisLoggedIn} />}
        />
        <Route path='addOffer' element={<AddOffer />} />
        <Route path='myProfile' element={<MyProfile />} />
        <Route path='myOffers' element={<MyOffers />} />
        <Route path='details/:id' element={<OfferDetail />} />
        <Route path='editOffer/:id' element={<EditOffer />} />
        <Route path='*' element={<NotFound />} />
      </Routes>
    </div>
  );
}

export default App;
