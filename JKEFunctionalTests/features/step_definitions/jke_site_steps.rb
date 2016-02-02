Given "I have a jke web server" do
  @server_address = "http://#{ENV["SERVER_ADDRESS"]}:8080"
  @http_client = HTTPClient.new
end

Given "I have a user account" do
  @user = "jbrown"
  @password = "jbrown"
end

Then "I should be able to visit the homepage" do
puts @server_address
	response = @http_client.get(@server_address)
  assert_equal(response.status, HTTP::Status::OK, "Homepage failed to load")
end

Then "I should be able to login to the user account" do
	url = "#{@server_address}/user/#{@user}"
    response = @http_client.get(url)
    assert_equal(response.status, HTTP::Status::OK, "Error logging in user #{url}")
end

Then "I should not be able to contribute a negative amount" do
	url = "#{@server_address}/transactions/create?account=200&org=American%20Cancer%20Society&date=31+Aug+2010&percent=-5"
  response = @http_client.post(url)
  assert_equal(response.status, HTTP::Status::BAD_REQUEST, "Expected bad request but request was accepted")
end

Then "Then I should be able to get a stock quote for 'IBM'" do
	url = "#{@server_address}/quote/IBM"
	response = @http_client.get(url)
	assert_equal(response.status, HTTP::Status::OK, "Error requesting stock quote for 'IBM'")
end
