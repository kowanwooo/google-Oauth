<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>구글 로그인</title>
<script src="http://code.jquery.com/jquery-3.2.1.js"></script>
<script type="text/javascript">
	//구글 로그인 버튼 클릭
	function loginWithGoogle() {
		$.ajax({
			url : '/googleOauth/getGoogleAuthUrl',
			type : 'get',
		}).done(function(res) {
			location.href = res;
		});
	}
</script>
</head>
<body>
	<button onclick="loginWithGoogle()">구글!</button>
</body>
</html>