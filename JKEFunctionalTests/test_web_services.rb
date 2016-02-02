################################################################################
# Dependencies                                                                 #
################################################################################
require 'rubygems'
require 'test/unit'
require 'httpclient'
require 'httpclient/http'

################################################################################
# Class Definition                                                             #
################################################################################

class JKEFunctionalTests < Test::Unit::TestCase
  @mtm_url_base = "http://localhost:8080"
  MTM_USER = "jbrown"
  MTM_PASSWORD = "jbrown"
  
  def JKEFunctionalTests.mtm_url_base=(ip_address)
  	@mtm_url_base = "http://#{ip_address}:8080"
	end
	
  def setup
    @http_client = HTTPClient.new
    puts @mtm_url_base
  end
  
  def test_homepage
    response = @http_client.get("#{MTM_URL_BASE}/")
    assert_equal(response.status, HTTP::Status::OK, "Homepage failed to load")
  end
  
  def test_login
    url = "#{MTM_URL_BASE}/user/#{MTM_USER}"
    response = @http_client.get(url)
    assert_equal(response.status, HTTP::Status::OK, "Error logging in user #{url}")
  end
  
  def test_negative_contribution
    url = "#{MTM_URL_BASE}/transactions/create?account=200&org=American%20Cancer%20Society&date=31+Aug+2010&percent=-5"
    response = @http_client.post(url)
    assert_equal(response.status, HTTP::Status::BAD_REQUEST, "Expected bad request but request was accepted")
  end
  
end

