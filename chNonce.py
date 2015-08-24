"""
The proxyRequest and proxyResponse functions will be called for all requests  and responses made via ZAP, 
excluding some of the automated tools
If they return 'false' then the corresponding request / response will be dropped. 
You can use msg.setForceIntercept(true) in either method to force a break point

Note that new proxy scripts will initially be disabled
Right click the script in the Scripts tree and select "enable"  
"""
import re


def generateNonce():
	""" Documentation """
	import uuid
	import hashlib
	import base64

	guid = uuid.uuid1()
	hash = hashlib.sha1(guid.bytes)
	nonce = base64.b64encode(hash.hexdigest())

	return nonce


def getNowTime(ttl):
	""" Documentation """
	from datetime import datetime, timedelta
	created = datetime.utcnow()
	expires = created + timedelta(seconds=ttl)
	return (created.strftime("%Y-%m-%dT%H:%M:%SZ"), expires.strftime("%Y-%m-%dT%H:%M:%SZ"))


def proxyRequest(msg):
	""" Documentation """

	if msg.getRequestHeader().getMethod() == "POST":

		(created, expires) = getNowTime(60)
		body = msg.getRequestBody().toString()
		m = re.search("\<wsu:Created.*\>(.*)\</wsu:Created\>\<wsu:Expires\>", body)
		body = body.replace(m.group(1), created)
		m = re.search("\<wsu:Expires\>(.*)\</wsu:Expires\>", body)
		body = body.replace(m.group(1), expires)
		m = re.search("\<wsse:Nonce.*\>(.*)\</wsse:Nonce\>", body)
		body = body.replace(m.group(1), generateNonce())
		m = re.search("\</wsse:Nonce\>\<wsu:Created\>(.*)\</wsu:Created\>", body)
		body = body.replace(m.group(1), created)
		tam = len(body)
		msg.getRequestHeader().setHeader("Content-Length", str(tam))
		msg.setRequestBody(body)

	return True


def proxyResponse(msg):
	""" Documentation """
	return True
