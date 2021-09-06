package dev.vini2003.skinshuffle.common.util

import com.google.gson.JsonParser
import com.mojang.authlib.GameProfile
import org.apache.commons.io.IOUtils
import java.net.URL
import java.nio.charset.StandardCharsets
import java.util.*

fun getProfile(username: String): GameProfile {
	val apiUrl = "https://api.ashcon.app/mojang/v2/user/$username"
	
	val apiData = IOUtils.toString(URL(apiUrl), StandardCharsets.UTF_8)
	
	val apiObject = JsonParser().parse(apiData).asJsonObject
	
	val apiUsername = apiObject.get("username").asString
	
	return GameProfile(UUID.fromString(apiObject.get("uuid").asString), apiUsername)
}