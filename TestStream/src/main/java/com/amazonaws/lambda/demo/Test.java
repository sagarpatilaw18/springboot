package com.amazonaws.lambda.demo;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Test {

	public static void main(String[] args) {
		String encoded = "H4sIAAAAAAAAAEVQy2rDMBD8lSJ6TLAeltbyzVA30AcUbOghDkGulURgS8FWYkLIv1dKaXsZltnZGXauaNDTpPa6vhw1ytFTURfb97KqilWJFsjNVo+B5pILITnOGMkC3bv9anSnY9gkap6SXg1tp5KPXhm7fFFntaz15H+ElR+1GoKSYkoTTBKcJuvHt6Iuq3oDMgu2adYCC8h3kkpoNRVYtpgQUMFiOrXT12iO3jj7bHqvxwnla+RjwOaeUJ619ZG8ItOFICYEpoyBIIxy4AQkzWTKGAYqpEjTlBKBATPBACRwwCSTQCE+5k2ow6shfEZEShjmPGOCksVvTcG+Qa3rLg3KHxp0bZomHPlexyGPUIXrXr86u1PtqVfejZ+m22sfd4sIVg3/6iE0tp2N7dz8J5hN5w93Bcf4zhy02R/8L3WLOotum9s3FDnbib4BAAA=";
		encodeData(encoded);
	}

	public static JSONObject encodeData(String encoded) {

		JSONObject outputJsonObject = null;

		Base64 base64 = new Base64();

		byte[] compressed = base64.decode(encoded);
		// String data = new String(compressed);
		// System.out.println(data);

		if ((compressed == null) || (compressed.length == 0)) {
			throw new IllegalArgumentException("Cannot unzip null or empty bytes");
		}
		if (!isZipped(compressed)) {
			System.out.println(compressed);
		}

		try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(compressed)) {
			try (GZIPInputStream gzipInputStream = new GZIPInputStream(byteArrayInputStream)) {
				try (InputStreamReader inputStreamReader = new InputStreamReader(gzipInputStream,
						StandardCharsets.UTF_8)) {
					try (BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
						StringBuilder output = new StringBuilder();
						String line;
						while ((line = bufferedReader.readLine()) != null) {
							output.append(line);
							System.out.println(output.toString());
						}
						System.out.println("Message : " + output.toString());

						try {
							JSONObject jsonObject = new JSONObject(output.toString());
							System.out.println(jsonObject.get("logEvents"));
							System.out.println("JSONObject " + jsonObject.toString());

							JSONArray arr = jsonObject.getJSONArray("logEvents");

							for (int i = 0; i < arr.length(); i++) {
								System.out.println("JSON Message : " + arr.getJSONObject(i).getString("message"));

								StringBuilder sb = new StringBuilder();
								sb.append("{");
								if (arr.getJSONObject(i).getString("message") != null)
									sb.append(arr.getJSONObject(i).getString("message"));
								sb.append("}");

								JSONObject jsonObjects = new JSONObject(
										"{" + arr.getJSONObject(i).getString("message") + "}");
								// System.out.println("Before IF " + jsonObjects.toString());
								if (jsonObjects.has("body")) {
									String jsonBody = jsonObjects.getString("body");
									System.out.println("Final Json Body : " + jsonBody);
									outputJsonObject = new JSONObject(jsonBody);
								}

							}
						} catch (JSONException err) {
							System.out.println("Error " + err.toString());
						}
					}

				}
			}
		} catch (IOException e) {
			throw new RuntimeException("Failed to unzip content", e);
		}
		return outputJsonObject;
	}

	public static boolean isZipped(final byte[] compressed) {
		return (compressed[0] == (byte) (GZIPInputStream.GZIP_MAGIC))
				&& (compressed[1] == (byte) (GZIPInputStream.GZIP_MAGIC >> 8));
	}
}
