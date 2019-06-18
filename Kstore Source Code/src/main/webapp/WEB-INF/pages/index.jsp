<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>解忧杂货店</title>
<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="robots" content="all,follow">
<!-- Price Slider Stylesheets -->
<link rel="stylesheet" href="vendor/nouislider/nouislider.css">
<!-- Google fonts - Playfair Display-->
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css?family=Playfair+Display:400,400i,700">
<link rel="stylesheet" href="fonts/hkgrotesk/stylesheet.css">
<!-- owl carousel-->
<link rel="stylesheet"
	href="vendor/owl.carousel/assets/owl.carousel.css">
<!-- Ekko Lightbox-->
<link rel="stylesheet" href="vendor/ekko-lightbox/ekko-lightbox.css">
<!-- theme stylesheet-->
<link rel="stylesheet" href="css/style.default.css"
	id="theme-stylesheet">
<!-- Custom stylesheet - for your changes-->
<link rel="stylesheet" href="css/custom.css">
<!-- Favicon-->
<link rel="shortcut icon" href="img/favicon.png">
<!-- Tweaks for older IEs-->
<!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
        <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script><![endif]-->
<!-- Font Awesome CSS-->
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.1.0/css/solid.css"
	integrity="sha384-TbilV5Lbhlwdyc4RuIV/JhD8NR+BfMrvz4BL5QFa2we1hQu6wvREr3v6XSRfCTRp"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.1.0/css/regular.css"
	integrity="sha384-avJt9MoJH2rB4PKRsJRHZv7yiFZn8LrnXuzvmZoD3fh1aL6aM6s0BBcnCvBe6XSD"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.1.0/css/brands.css"
	integrity="sha384-7xAnn7Zm3QC1jFjVc1A6v/toepoG3JXboQYzbM0jrPzou9OFXm/fY6Z/XiIebl/k"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.1.0/css/fontawesome.css"
	integrity="sha384-ozJwkrqb90Oa3ZNb+yKFW2lToAWYdTiF1vt8JiH5ptTGHTGcN7qdoR1F95e0kYyG"
	crossorigin="anonymous">
</head>
<body>
	<header class="header header-absolute">
		<!-- Navbar-->
		<nav
			class="navbar navbar-expand-lg bg-transparent navbar-dark bg-hover-white bg-fixed-white navbar-hover-light navbar-fixed-light">
			<div class="container-fluid">
				<!-- Navbar Header  -->
				<a href="index" class="navbar-brand"><span
					class="font-weight-bold text-uppercase">解忧杂货店<span
						class="text-primary">.</span>
				</span></a>
				<button type="button" data-toggle="collapse"
					data-target="#navbarCollapse" aria-controls="navbarCollapse"
					aria-expanded="false" aria-label="Toggle navigation"
					class="navbar-toggler navbar-toggler-right">
					<i class="fa fa-bars"></i>
				</button>
				<!-- Navbar Collapse -->
				<div id="navbarCollapse" class="collapse navbar-collapse">
					<ul class="navbar-nav mx-auto">
						<li class="nav-item"><a href="index" class="nav-link">首页</a>
						<li class="nav-item"><a href="category-no-sidebar"
							class="nav-link">商品</a></li>

						<li class="nav-item"><a href="contact" class="nav-link">联系</a>
						</li>

						<li class="nav-item"><a href="coming-soon" class="nav-link">拭目以待</a>
						</li>
					</ul>
					<div
						class="right-col d-flex align-items-center justify-content-between justify-content-lg-end mt-1 mb-2 my-lg-0">
						<!-- Search Button-->
						<div data-toggle="search" class="nav-item navbar-icon-link">
							<svg class="svg-icon">
                  <use xlink:href="#search-1"> </use>
                </svg>
						</div>
						<!-- User Not Logged - link to login page-->
						<div class="nav-item">
							<a href="login" class="navbar-icon-link"> <svg
									class="svg-icon">
                    <use xlink:href="#male-user-1"> </use>
                  </svg><span
								class="text-sm ml-2 ml-lg-0 text-uppercase text-sm font-weight-bold d-none d-sm-inline d-lg-none">Log
									in </span>
							</a>
						</div>
						
						<!-- Post -->
						<div data-toggle="modal" data-target="#postModal" class="nav-item navbar-icon-link">
                            <svg class="svg-icon">
                                <use xlink:href="#basket-1"> </use>
                            </svg>
                        </div>
						
						<!-- Cart Dropdown-->
						<div class="nav-item dropdown">
							<a href="cart" class="navbar-icon-link d-lg-none"> <svg
									class="svg-icon">
                    <use xlink:href="#cart-1"> </use>
                  </svg><span
								class="text-sm ml-2 ml-lg-0 text-uppercase text-sm font-weight-bold d-none d-sm-inline d-lg-none">View
									cart</span>
							</a>
							<div class="d-none d-lg-block">
								<a id="cartdetails" data-toggle="dropdown" aria-haspopup="true"
									aria-expanded="false" href="cart.html"
									class="navbar-icon-link dropdown-toggle"> <svg
										class="svg-icon">
                      <use xlink:href="#cart-1"> </use>
                    </svg>
									<div class="navbar-icon-link-badge">${cartbarcount}</div>
								</a>
								<div aria-labelledby="cartdetails"
									class="dropdown-menu dropdown-menu-right p-4">
									<!-- cart item-->
									<c:if test="${!empty cartbargoods}">
										<c:forEach items="${cartbargoods}" var="cartbargoods">
											<div class="navbar-cart-product">
												<div class="d-flex align-items-center">
													<a href="detail?detailnum=${cartbargoods.goods_id }"><img
														src="${cartbargoods.pictures}" alt="..."
														class="img-fluid navbar-cart-product-image"></a>
													<div class="d-flex w-100 justify-content-between">
														<div class="pl-3">
															<a href="detail?detailnum=${cartbargoods.goods_id }"
																class="navbar-cart-product-link">${cartbargoods.name}</a><small
																class="d-block text-muted">${cartbargoods.type}</small><strong
																class="d-block text-sm">${cartbargoods.price}
																RMB</strong>
														</div>
														<a href="#"><i class="fa fa-trash-o"></i></a>
													</div>
												</div>
											</div>
										</c:forEach>
									</c:if>
									<!-- total price-->
									<div class="navbar-cart-total">
										<span class="text-uppercase text-muted">Total</span><strong
											class="text-uppercase">${cartbartotal} RMB</strong>
									</div>
									<!-- buttons-->
									<div class="d-flex justify-content-between">
										<a href="cart" class="btn btn-link text-dark mr-3">查看购物车 <i
											class="fa-arrow-right fa"></i></a><a href="checkout1.html"
											class="btn btn-outline-dark">结算</a>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</nav>
		<!-- /Navbar -->

		<!-- Fullscreen search area-->
		<div class="search-area-wrapper">
			<div
				class="search-area d-flex align-items-center justify-content-center">
				<div class="close-btn">
					<svg class="svg-icon svg-icon-light w-3rem h-3rem">
              <use xlink:href="#close-1"> </use>
            </svg>
				</div>
				<form autocomplete="off" action="searchgoods" class="search-area-form">
					<div class="row">
						<h6 class="text-muted text-center">
							<span class="col-lg-4 text-muted font-italic font-weight-light"
								href="https://www.baidu.com"> <svg class="svg-icon">
									<use xlink:href="#search-1"></use></svg>热搜榜：
							</span> 
							<c:forEach items="${searchlist}" var="search">
							<a class="col-lg-4 text-muted font-italic font-weight-light"
								href="searchgoods?search=${search.content }"> <svg class="svg-icon">
									<use xlink:href="#money-box-1"></use></svg>${search.content }
							</a> 
							</c:forEach>
						</h6>
					</div>
					<div class="form-group position-relative">
						<input type="search" name="search" id="search"
							placeholder="What are you looking for?" class="search-area-input">
						<button type="submit" class="search-area-button">
							<svg class="svg-icon">
                  <use xlink:href="#search-1"> </use>
                </svg>
						</button>
					</div>
				</form>
			</div>
		</div>
		<!-- /Fullscreen search area-->

	</header>
	<section data-parallax="scroll"
		data-image-src="img/photo/macbook_pro.jpg" data-speed="0.5"
		class="h-100vh d-flex align-items-center text-white text-center">
		<div class="container">
			<div class="row">
				<div class="col-xl-8 col-lg-10 py-5 overflow-hidden mx-auto">
					<h5
						class="text-uppercase text-white text-shadow font-weight-normal mb-4 letter-spacing-5">
						Just arrived</h5>
					<h1
						class="text-shadow mb-5 display-2 font-weight-bold text-uppercase">数码电子</h1>
					<p>
						<a href="digital-product" class="btn btn-light">See look book</a>
					</p>
				</div>
			</div>
		</div>
	</section>

	<section data-parallax="scroll"
		data-image-src="img/photo/Ready-Player-One-9-1800x1200.jpg"
		data-speed="0.5"
		class="h-100vh d-flex align-items-center text-white text-center">
		<div class="container">
			<div class="row">
				<div class="col-xl-8 col-lg-10 py-5 overflow-hidden mx-auto">
					<h1
						class="text-shadow mb-5 display-1 font-weight-bold text-uppercase">游戏交易</h1>
					<p>
						<a href="game-trade" class="btn btn-light">See look book</a>
					</p>
				</div>
			</div>
		</div>
	</section>

	<section data-parallax="scroll" data-image-src="img/photo/apic3084.jpg"
		data-speed="0.5"
		class="h-100vh d-flex align-items-center text-white text-center">
		<div class="container">
			<div class="row">
				<div class="col-xl-8 col-lg-10 py-5 overflow-hidden mx-auto">
					<h5
						class="text-uppercase text-white text-shadow font-weight-normal mb-4 letter-spacing-5">
						Our favourites</h5>
					<h1
						class="text-shadow mb-5 display-3 font-weight-bold text-uppercase">生活家居</h1>
					<p>
						<a href="home-life" class="btn btn-light">See look book</a>
					</p>
				</div>
			</div>
		</div>
	</section>

	<section data-parallax="scroll"
		data-image-src="img/photo/malvestida-magazine-458585-unsplash.jpg"
		data-speed="0.5"
		class="h-100vh d-flex align-items-center text-center text-white">
		<div class="container">
			<div class="row">
				<div class="col-xl-8 col-lg-10 py-5 overflow-hidden mx-auto">
					<h1
						class="text-shadow mb-5 display-3 font-weight-bold text-uppercase">服饰鞋包</h1>
					<p>
						<a href="clothes-shoes" class="btn btn-light">See look book</a>
					</p>
				</div>
			</div>
		</div>
	</section>

	<section data-parallax="scroll"
		data-image-src="img/photo/353473-svetik.jpg" data-speed="0.5"
		class="h-100vh d-flex align-items-center text-white text-center">
		<div class="container">
			<div class="row">
				<div class="col-xl-8 col-lg-10 py-5 overflow-hidden mx-auto">
					<h5
						class="text-uppercase text-white text-shadow font-weight-normal mb-4 letter-spacing-5">
						Just arrived</h5>
					<h1
						class="text-shadow mb-5 display-2 font-weight-bold text-uppercase">日常百货</h1>
					<p>
						<a href="everything" class="btn btn-light">See look book</a>
					</p>
				</div>
			</div>
		</div>
	</section>
	
	<!--        post modal starts here-->

		<div id="postModal" tabindex="-1" role="dialog" aria-hidden="true"
			class="modal fade quickview">
			<form class="form-group" action="goodspost" method="post"
				enctype="multipart/form-data">
				<div role="document" class="modal-dialog modal-lg">
					<div class="modal-content">
						<button type="button" data-dismiss="modal" aria-label="Close"
							class="close modal-close">
							<svg class="svg-icon w-100 h-100 svg-icon-light align-middle">
              <use xlink:href="#close-1"> </use>
            </svg>
						</button>
						<div class="modal-body">
							<div class="ribbon ribbon-primary">Your Posts</div>
							<div class="row">
								<div class="col-lg-6">
									<link rel="stylesheet" type="text/css"
										href="css/file-upload-with-preview.min.css">

									<div class="custom-file-container"
										data-upload-id="myUniqueUploadId">
										<label>Upload File <a href="javascript:void(0)"
											class="custom-file-container__image-clear"
											title="Clear Image"></a></label> <label
											class="custom-file-container__custom-file"> <input
											type="file" name="goodsphoto"
											class="custom-file-container__custom-file__custom-file-input"
											accept="*" multiple> <input type="hidden"
											name="MAX_FILE_SIZE" value="10485760" /> <span
											class="custom-file-container__custom-file__custom-file-control"></span>
										</label>
										<div class="custom-file-container__image-preview"
											style="height: 500px"></div>
									</div>

									<script src="js/file-upload-with-preview.min.js"></script>
									<script>
										var upload = new FileUploadWithPreview(
												'myUniqueUploadId')
									</script>
								</div>
								<div class="col-lg-6 align-items-center">
									<br> <br> <label for="product-title"
										class="text-muted"><h4>商品标题</h4></label> <br> <input
										type="text" name="goodstitle" id="product-title"
										class="form-control" required oninvalid="this.setCustomValidity('标题不能为空！')" oninput="this.setCustomValidity('')">
									<hr>
									<label for="product-detail" class="text-muted"><h4>商品分类</h4></label>
									<br> <select class="form-control" name="goodstype">
										<option class="form-control" value="数码电子">数码电子</option>
										<option class="form-control" value="游戏交易">游戏交易</option>
										<option class="form-control" value="生活家居">生活家居</option>
										<option class="form-control" value="服饰鞋包">服饰鞋包</option>
										<option class="form-control" value="生活百货">生活百货</option>
										<option class="form-control" value="其它产品">其它产品</option>
									</select>
									<hr>
									<label for="product-detail" class="text-muted"><h4>商品详情</h4></label>
									<br>

									<textarea class="form-control" name="goodsdescription"
										id="product-detail" rows="4" required oninvalid="this.setCustomValidity('商品详情不能为空！')" oninput="this.setCustomValidity('')"></textarea>
									<hr>

									<label for="product-detail" class="text-muted"><h4>商品价格</h4></label>

									<div class="input-group mb-2 mr-sm-2">
										<div class="input-group-prepend">
											<div class="input-group-text">¥</div>
										</div>
										<input type="number" name="goodsprice" class="form-control"
											id="product-price" required oninvalid="this.setCustomValidity('商品价格不能为空！')" oninput="this.setCustomValidity('')">
									</div>
									<hr>


									<div>
										<button type="submit" id="post-product"
											class="btn btn-dark float-right">
											<i class="fa fa-shopping-cart mr-2"></i>发布商品
										</button>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</form>
		</div>

		<!--        modal ends here-->

	<!-- Footer-->
	<footer class="main-footer">
		<!-- Services block-->
		<div class="bg-gray-100 text-dark-700 py-6">
			<div class="container">
				<div class="row">
					<div class="col-lg-4 service-column">
						<svg class="svg-icon service-icon">
                <use xlink:href="#delivery-time-1"> </use>
              </svg>
						<div class="service-text">
							<h6 class="text-uppercase">官方邮递 &amp; 支持外包</h6>
							<p class="text-muted font-weight-light text-sm mb-0">超过¥300免费</p>
						</div>
					</div>
					<div class="col-lg-4 service-column">
						<svg class="svg-icon service-icon">
                <use xlink:href="#money-1"> </use>
              </svg>
						<div class="service-text">
							<h6 class="text-uppercase">退款保证</h6>
							<p class="text-muted font-weight-light text-sm mb-0">7天无理由退款</p>
						</div>
					</div>
					<div class="col-lg-4 service-column">
						<svg class="svg-icon service-icon">
                <use xlink:href="#customer-support-1"> </use>
              </svg>
						<div class="service-text">
							<h6 class="text-uppercase">020-800-456-747</h6>
							<p class="text-muted font-weight-light text-sm mb-0">24/7
								人工客服</p>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- Main block - menus, subscribe form-->
		<div class="py-6 bg-gray-300 text-muted">
			<div class="container">
				<div class="row">
					<div class="col-lg-4 mb-5 mb-lg-0">
						<div
							class="font-weight-bold text-uppercase text-lg text-dark mb-3">
							解忧杂货店<span class="text-primary">.</span>
						</div>
						<p>每个带着烦恼来找我的人，他们心里都有答案</p>
						<ul class="list-inline">
							<li class="list-inline-item"><a href="#" target="_blank"
								title="twitter" class="text-muted text-hover-primary"><i
									class="fab fa-twitter"></i></a></li>
							<li class="list-inline-item"><a href="#" target="_blank"
								title="facebook" class="text-muted text-hover-primary"><i
									class="fab fa-facebook"></i></a></li>
							<li class="list-inline-item"><a href="#" target="_blank"
								title="instagram" class="text-muted text-hover-primary"><i
									class="fab fa-instagram"></i></a></li>
							<li class="list-inline-item"><a href="#" target="_blank"
								title="pinterest" class="text-muted text-hover-primary"><i
									class="fab fa-pinterest"></i></a></li>
							<li class="list-inline-item"><a href="#" target="_blank"
								title="vimeo" class="text-muted text-hover-primary"><i
									class="fab fa-vimeo"></i></a></li>
						</ul>
					</div>
					<div class="col-lg-2 col-md-6 mb-5 mb-lg-0">
						<h6 class="text-uppercase text-dark mb-3">Shop</h6>
						<ul class="list-unstyled">
							<li><a href="#" class="text-muted">For Women</a></li>
							<li><a href="#" class="text-muted">For Men</a></li>
							<li><a href="#" class="text-muted">Stores</a></li>
							<li><a href="#" class="text-muted">Our Blog</a></li>
							<li><a href="#" class="text-muted">Shop</a></li>
						</ul>
					</div>
					<div class="col-lg-2 col-md-6 mb-5 mb-lg-0">
						<h6 class="text-uppercase text-dark mb-3">Company</h6>
						<ul class="list-unstyled">
							<li><a href="#" class="text-muted">Login </a></li>
							<li><a href="#" class="text-muted">Register </a></li>
							<li><a href="#" class="text-muted">Wishlist </a></li>
							<li><a href="#" class="text-muted">Our Products </a></li>
							<li><a href="#" class="text-muted">Checkouts </a></li>
						</ul>
					</div>
					<div class="col-lg-4">
						<h6 class="text-uppercase text-dark mb-3">每日打折 & 限时活动</h6>
						<p class="mb-3">订阅我们来获得最新信息.</p>
						<form action="#" id="newsletter-form">
							<div class="input-group mb-3">
								<input type="email" placeholder="Your Email Address"
									aria-label="输入你的Email"
									class="form-control bg-transparent border-secondary border-right-0">
								<div class="input-group-append">
									<button type="submit"
										class="btn btn-outline-secondary border-left-0">
										<i class="fa fa-paper-plane text-lg text-dark"></i>
									</button>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		<!-- Copyright section of the footer-->
		<div class="py-4 font-weight-light bg-gray-800 text-gray-300">
			<div class="container">
				<div class="row align-items-center">
					<div class="col-md-6 text-center text-md-left">
						<p class="mb-md-0">&copy; 2018 解忧杂货店. All rights reserved.</p>
					</div>
					<div class="col-md-6">
						<ul
							class="list-inline mb-0 mt-2 mt-md-0 text-center text-md-right">
							<li class="list-inline-item"><img src="img/visa.svg"
								alt="..." class="w-2rem"></li>
							<li class="list-inline-item"><img src="img/mastercard.svg"
								alt="..." class="w-2rem"></li>
							<li class="list-inline-item"><img src="img/paypal.svg"
								alt="..." class="w-2rem"></li>
							<li class="list-inline-item"><img
								src="img/western-union.svg" alt="..." class="w-2rem"></li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</footer>
	<!-- /Footer end-->
	<div id="scrollTop">
		<i class="fa fa-long-arrow-alt-up"></i>
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
	<script src="vendor/popper.js/umd/popper.min.js">
		
	</script>
	<script src="vendor/bootstrap/js/bootstrap.min.js"></script>
	<script src="vendor/jquery.cookie/jquery.cookie.js">
		
	</script>
	<script src="vendor/owl.carousel/owl.carousel.js"></script>
	<script src="vendor/owl.carousel2.thumbs/owl.carousel2.thumbs.min.js"></script>
	<script src="vendor/nouislider/nouislider.min.js"></script>
	<script src="vendor/smooth-scroll/smooth-scroll.polyfills.min.js"></script>
	<script src="vendor/ekko-lightbox/ekko-lightbox.min.js"></script>
	<script src="js/theme.js"></script>
	<script src="vendor/jquery-parallax.js/parallax.min.js"></script>

</body>
</html>