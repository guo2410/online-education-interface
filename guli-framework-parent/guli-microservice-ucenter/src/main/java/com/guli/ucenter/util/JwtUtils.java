package com.guli.ucenter.util;

import com.guli.ucenter.entity.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang.StringUtils;

import java.util.Date;

public class JwtUtils {

	public static final String SUBJECT = "guli";

	public static final String APPSECRET = "guli";

	public static final long EXPIRE = 1000 * 60 * 30;  //过期时间，毫秒，30分钟


	public static String geneJsonWebToken(Member member) {

		if (member == null || StringUtils.isEmpty(member.getId())
				|| StringUtils.isEmpty(member.getNickname())
				|| StringUtils.isEmpty(member.getAvatar())) {
			return null;

		}
		String token = Jwts.builder().setSubject(SUBJECT)
				.claim("id", member.getId())
				.claim("nickname", member.getNickname())
				.claim("avatar", member.getAvatar())
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
				.signWith(SignatureAlgorithm.HS256, APPSECRET).compact();
		return token;
	}

	public static Claims checkJWT(String token) {
		Claims claims = Jwts.parser().setSigningKey(APPSECRET).parseClaimsJws(token).getBody();
		return claims;
	}

	private static String testGeneJwt(){
		Member member = new Member();
		member.setId("999");
		member.setAvatar("www.guli.com");
		member.setNickname("Helen");

		String token = JwtUtils.geneJsonWebToken(member);
		System.out.println(token);
		return token;
	}
	private static void testCheck(String token){

		Claims claims = JwtUtils.checkJWT(token);
		String nickname = (String)claims.get("nickname");
		String avatar = (String)claims.get("avatar");
		String id = (String)claims.get("id");
		System.out.println(nickname);
		System.out.println(avatar);
		System.out.println(id);

	}

	public static void main(String[] args){
		String token = testGeneJwt();
		testCheck(token);
	}
}