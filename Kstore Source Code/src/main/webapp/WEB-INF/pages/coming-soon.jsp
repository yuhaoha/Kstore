<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>拭目以待</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="robots" content="all,follow">
    <!-- Price Slider Stylesheets -->
    <link rel="stylesheet" href="vendor/nouislider/nouislider.css">
    <!-- Google fonts - Playfair Display-->
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Playfair+Display:400,400i,700">
    <link rel="stylesheet" href="fonts/hkgrotesk/stylesheet.css">
    <!-- owl carousel-->
    <link rel="stylesheet" href="vendor/owl.carousel/assets/owl.carousel.css">
    <!-- Ekko Lightbox-->
    <link rel="stylesheet" href="vendor/ekko-lightbox/ekko-lightbox.css">
    <!-- theme stylesheet-->
    <link rel="stylesheet" href="css/style.default.css" id="theme-stylesheet">
    <!-- Custom stylesheet - for your changes-->
    <link rel="stylesheet" href="css/custom.css">
    <!-- Favicon-->
    <link rel="shortcut icon" href="img/favicon.png">
    <!-- Tweaks for older IEs--><!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
        <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script><![endif]-->
    <!-- Font Awesome CSS-->
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.1.0/css/solid.css" integrity="sha384-TbilV5Lbhlwdyc4RuIV/JhD8NR+BfMrvz4BL5QFa2we1hQu6wvREr3v6XSRfCTRp" crossorigin="anonymous">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.1.0/css/regular.css" integrity="sha384-avJt9MoJH2rB4PKRsJRHZv7yiFZn8LrnXuzvmZoD3fh1aL6aM6s0BBcnCvBe6XSD" crossorigin="anonymous">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.1.0/css/brands.css" integrity="sha384-7xAnn7Zm3QC1jFjVc1A6v/toepoG3JXboQYzbM0jrPzou9OFXm/fY6Z/XiIebl/k" crossorigin="anonymous">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.1.0/css/fontawesome.css" integrity="sha384-ozJwkrqb90Oa3ZNb+yKFW2lToAWYdTiF1vt8JiH5ptTGHTGcN7qdoR1F95e0kYyG" crossorigin="anonymous">
  </head>
  <body>
    <div class="mh-full-screen d-flex align-items-center dark-overlay"><img src="img/photo/pete-bellis-189599-unsplash-big.jpg" class="bg-image">
      <div class="container text-white text-center text-lg overlay-content py-6 py-lg-0">
        <h1 class="text-hide mx-auto mb-6">Coming soon<img src="img/coming-soon.png" class="img-fluid"></h1>
        <h3 class="mb-5">Our store is coming to you soon.</h3>
        <p class="mb-4"><i class="fas fa-map-marker-alt mr-2"></i> 南开大学软件学院</p>
        <ul class="list-inline">
          <li class="list-inline-item"><a href="#" target="_blank" title="twitter" class="text-white"><i class="fab fa-twitter">           </i></a></li>
          <li class="list-inline-item"><a href="#" target="_blank" title="facebook" class="text-white"><i class="fab fa-facebook">           </i></a></li>
          <li class="list-inline-item"><a href="#" target="_blank" title="instagram" class="text-white"><i class="fab fa-instagram">           </i></a></li>
          <li class="list-inline-item"><a href="#" target="_blank" title="pinterest" class="text-white"><i class="fab fa-pinterest">           </i></a></li>
          <li class="list-inline-item"><a href="#" target="_blank" title="vimeo" class="text-white"><i class="fab fa-vimeo">           </i></a></li>
        </ul>
      </div>
    </div>
    <!-- JavaScript files-->
    <script>
      // ------------------------------------------------------- //
      //   Inject SVG Sprite - 
      //   see more here 
      //   https://css-tricks.com/ajaxing-svg-sprite/
      // ------------------------------------------------------ //
      function injectSvgSprite(path) {
      
          var ajax = new XMLHttpRequest();
          ajax.open("GET", path, true);
          ajax.send();
          ajax.onload = function(e) {
          var div = document.createElement("div");
          div.className = 'd-none';
          div.innerHTML = ajax.responseText;
          document.body.insertBefore(div, document.body.childNodes[0]);
          }
      }    
      injectSvgSprite('icons/orion-svg-sprite.svg'); 
      
    </script>
    <script src="vendor/jquery/jquery.min.js"></script>
    <script src="vendor/popper.js/umd/popper.min.js"> </script>
    <script src="vendor/bootstrap/js/bootstrap.min.js"></script>
    <script src="vendor/jquery.cookie/jquery.cookie.js"> </script>
    <script src="vendor/owl.carousel/owl.carousel.js"></script>
    <script src="vendor/owl.carousel2.thumbs/owl.carousel2.thumbs.min.js"></script>
    <script src="vendor/nouislider/nouislider.min.js"></script>
    <script src="vendor/smooth-scroll/smooth-scroll.polyfills.min.js"></script>
    <script src="vendor/ekko-lightbox/ekko-lightbox.min.js"></script>
    <script src="js/theme.js"></script>
  </body>
</html>