<!DOCTYPE html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:url var="bootstrap_css"
	value="/webjars/bootstrap/4.6.0-1/css/bootstrap.min.css" />
<c:url var="bootstrap_js"
	value="/webjars/bootstrap/4.6.0-1/js/bootstrap.min.js" />
<c:url var="jquery_js" value="/webjars/jquery/3.5.1/jquery.min.js" />
<c:url var="css" value="/style.css" />
<c:url var="vue_js" value="/webjars/vue/3.2.19/dist/vue.global.js" />
<c:url var="axios_js" value="/webjars/axios/0.22.0/dist/axios.min.js" />

<html>
	<head>
	<meta charset="UTF-8">
	<title>Gestion de CV</title>
	<link rel="stylesheet" href="${css}">
	<link rel="stylesheet" href="${bootstrap_css}">
	<script src="${jquery_js}"></script>
	<script src="${bootstrap_js}"></script>
	<script src="${vue_js}"></script>
	<script src="${axios_js}"></script>
	<style>
	.bottone {margin-right:0;margin-left: auto;}
	.navva{display:flex;justify-content:center;align-items:center;background-color:#F8F8FF;box-shadow: 5px 5px 5px 5px #DCDCDC;margin-bottom:30px}
	#destra{margin-right:0;margin-left: auto;display:flex;flex-direction:row}
	#ricerca{display:flex;flex-direction:column;margin-right:10px}
	.inp_ricerca{margin-bottom:3px;width:250px}
	</style>
</head>
</head>
<body>
