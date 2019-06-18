<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>用户信息</title>
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
	<header class="header">
		<!-- Navbar-->
		<nav
			class="navbar navbar-expand-lg navbar-sticky navbar-light bg-white bg-fixed-white">
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
						<div data-toggle="modal" data-target="#postModal"
							class="nav-item navbar-icon-link">
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

	<!-- Hero Section-->
	<section class="hero">
		<div class="container">
			<!-- Breadcrumbs -->
			<ol class="breadcrumb justify-content-center">
				<li class="breadcrumb-item"><a href="index">Home</a></li>
				<li class="breadcrumb-item active">Your profile</li>
			</ol>
			<!-- Hero Content-->
			<div class="hero-content pb-5 text-center">
				<h1 class="hero-heading">个人信息</h1>
				<div class="row">
					<div class="col-xl-8 offset-xl-2">
						<p class="lead text-muted">澳门皇家赌场上线了</p>
					</div>
				</div>
			</div>
		</div>
	</section>
	<section>
		<div class="container">
			<div class="row">
				<div class="col-lg-8 col-xl-9">
					<div class="block mb-5">
						<div class="block-header">
							<strong class="text-uppercase">修改密码</strong>
						</div>
						<div class="block-body">
							<span id="checkpwdmessage" style="color: red"><%=request.getAttribute("checkpwdmessage") == null ? "" : request.getAttribute("checkpwdmessage")%></span>
							<form action="changepwd" method="post">
								<div class="row">
									<div class="col-sm-6">
										<div class="form-group">
											<label for="password_old" class="form-label">旧密码</label> <input
												id="password_old" name="oldpassword" required
												oninvalid="this.setCustomValidity('密码不能为空')"
												oninput="this.setCustomValidity('')" type="password"
												class="form-control">
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-sm-6">
										<div class="form-group">
											<label for="password_1" class="form-label">新密码</label> <input
												id="password_1" name="newpassword" required
												oninvalid="this.setCustomValidity('新密码不能为空')"
												oninput="this.setCustomValidity('')" type="password"
												class="form-control">
										</div>
									</div>
									<div class="col-sm-6">
										<div class="form-group">
											<label for="password_2" class="form-label">再次输入新密码</label> <input
												id="password_2" name="confirmpassword" required
												oninvalid="this.setCustomValidity('确认不能为空')"
												oninput="this.setCustomValidity('')" type="password"
												class="form-control">
										</div>
									</div>
								</div>
								<div class="text-center mt-4">
									<button type="submit" class="btn btn-outline-dark">
										<i class="far fa-save mr-2"></i>修改密码
									</button>
								</div>
							</form>
						</div>
					</div>
					<div class="block mb-5">
						<div class="block-header">
							<strong class="text-uppercase">个人信息详情</strong>
						</div>
						<div class="block-body">
							<span id="changeprofilemessage" style="color: red"><%=request.getAttribute("changeprofilemessage") == null
					? ""
					: request.getAttribute("changeprofilemessage")%></span>
							<form action="changeprofile" method="post">
								<div class="row">
									<div class="col-sm-6">
										<div class="form-group">

											<script src="vendor/jquery/jquery.min.js"></script>
											<script
												src="https://cdn.jsdelivr.net/npm/gijgo@1.9.10/js/gijgo.min.js"
												type="text/javascript"></script>
											<link
												href="https://cdn.jsdelivr.net/npm/gijgo@1.9.10/css/gijgo.min.css"
												rel="stylesheet" type="text/css" />

											<label for="datepicker" class="form-label">生日</label> <input
												id="datepicker" type="text" class="form-control"
												name="birthday">
										</div>

										<script>
											$('#datepicker').datepicker({
												uiLibrary : 'bootstrap4',
												format : 'yyyy-mm-dd'
											});
										</script>

									</div>
									<div class="col-sm-6">
										<div class="form-group">
											<label for="gender" class="form-label">性别</label> <select
												id="gender" class="form-control" name="gender">
												<option class="form-control" value="保密">保密</option>
												<option class="form-control" value="男">男</option>
												<option class="form-control" value="女">女</option>
											</select>
										</div>
									</div>
								</div>
								<!-- /.row-->
								<div class="row">
									<div class="col-sm-6">
										<div class="form-group">
											<label for="telephone" class="form-label">电话</label> <input
												id="telephone" type="text" class="form-control" name="phone">
										</div>
									</div>
									<div class="col-sm-6">
										<div class="form-group">
											<label for="email" class="form-label">Email</label> <input
												id="email" type="text" class="form-control" name="email">
										</div>
									</div>
								</div>
								<!-- /.row-->
								<div class="row">
									<div class="col-sm-6 col-md-3">
										<div class="form-group">
											<label for="province" class="form-label">省</label> <select
												id="cmbProvince" class="form-control" name="province">
											</select>
										</div>
									</div>
									<div class="col-sm-6 col-md-3">
										<div class="form-group">
											<label for="city" class="form-label">市</label> <select
												id="cmbCity" class="form-control" name="city">
											</select>
										</div>
									</div>
									<div class="col-sm-6">
										<div class="form-group">
											<label for="zone" class="form-label">区/县</label> <select
												id="cmbArea" class="form-control" name="district">
											</select>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-sm-12">
										<div class="form-group">
											<label for="street" class="form-label">街道</label> <input
												id="street" type="text" class="form-control" name="street">
										</div>
									</div>
								</div>
								
								<!-- /.row-->
								<div class="text-center mt-4">
									<button type="submit" class="btn btn-outline-dark">
										<i class="far fa-save mr-2"></i>Save changes
									</button>
								</div>
							</form>
						</div>
					</div>
				</div>
				<!-- Customer Sidebar-->
				<div class="col-xl-3 col-lg-4 mb-5">
					<div class="customer-sidebar card border-0">
						<div class="customer-profile">
							<a href="#" class="d-inline-block" data-toggle="modal"
								data-target="#modalPhoto"> <img height="160" width="160"
								onerror="this.src='img/photo/blog-avatar.jpg'"
								src="<%=request.getAttribute("url") == null ? "" : request.getAttribute("url")%>"
								class="rounded-circle customer-image"> <!-- Modal --> <!--上传头像参考资料https://www.npmjs.com/package/file-upload-with-preview-->
								<div class="modal fade" id="modalPhoto" tabindex="-1"
									role="dialog" aria-labelledby="exampleModalLabel"
									aria-hidden="true">
									<div class="modal-dialog" role="document">
										<div class="modal-content">
											
											<form action="changephoto" method="post"
												enctype="multipart/form-data">
												<div class="modal-body">
													<link rel="stylesheet" type="text/css"
														href="css/file-upload-with-preview.min.css">
													<div class="custom-file-container"
														data-upload-id="myUniqueUploadId">
														<label>上传头像<a href="javascript:void(0)"
															class="custom-file-container__image-clear"
															title="Clear Image"></a></label> <label
															class="custom-file-container__custom-file"> <input
															type="file" name="profilephoto"
															class="custom-file-container__custom-file__custom-file-input"
															accept="*" multiple> <input type="hidden"
															name="MAX_FILE_SIZE" value="10485760" /> <span
															class="custom-file-container__custom-file__custom-file-control"></span>
														</label>
														<div class="custom-file-container__image-preview" style="height: 440px !important; margin-top: 5px !important; margin-bottom: 5px !important"></div>
													</div>


													<script src="js/file-upload-with-preview.min.js"></script>
													<script>
														var upload = new FileUploadWithPreview(
																'myUniqueUploadId')
													</script>

												</div>
												<div class="modal-footer">
													<button type="button" class="btn btn-secondary"
														data-dismiss="modal">取消</button>
													<button type="submit" class="btn btn-primary">保存修改</button>
												</div>
											</form>
										</div>
									</div>
								</div>
							</a>
							<h5><%=request.getAttribute("profilename") == null ? "" : request.getAttribute("profilename")%></h5>
							<p class="text-muted text-sm mb-0"><%=request.getAttribute("profileprovince") == null
					? "还没有地址信息"
					: request.getAttribute("profileprovince")%>
								<%=request.getAttribute("profilecity") == null ? "" : request.getAttribute("profilecity")%></p>
							<hr>
							<span class="badge text-uppercase badge-dark p-2">信誉积分:<%=request.getAttribute("faith_valuemessage") == null
					? ""
					: request.getAttribute("faith_valuemessage")%></span>
						</div>
						<nav class="list-group customer-nav">
							<a href="orders"
								class="list-group-item d-flex justify-content-between align-items-center"><span>
									<svg class="svg-icon svg-icon-heavy mr-2">
                      <use xlink:href="#paper-bag-1"> </use>
                    </svg>Orders
							</span>
								<div class="badge badge-pill badge-dark font-weight-normal px-3">5</div></a>
							<a href="post"
								class="list-group-item d-flex justify-content-between align-items-center"><span>
									<svg class="svg-icon svg-icon-heavy mr-2">
                      <use xlink:href="#paper-bag-1"> </use>
                    </svg>Posts
							</span>
								<div
									class="badge badge-pill badge-light font-weight-normal px-3">5</div></a>
							<a href="#"
								class="active list-group-item d-flex justify-content-between align-items-center"><span>
									<svg class="svg-icon svg-icon-heavy mr-2">
                      <use xlink:href="#male-user-1"> </use>
                    </svg>Profile
							</span></a> <a href="logout"
								class="list-group-item d-flex justify-content-between align-items-center"><span>
									<svg class="svg-icon svg-icon-heavy mr-2">
                      <use xlink:href="#exit-1"> </use>
                    </svg>Log out
							</span></a>
						</nav>
					</div>
				</div>
				<!-- /Customer Sidebar-->
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
	<script src="js/china-address.js"></script>  
      <script type="text/javascript">  
          addressInit('cmbProvince', 'cmbCity', 'cmbArea');  
      </script>
	
</body>
</html>
