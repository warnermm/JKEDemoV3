Given "I have a jke web server" do
  @server_address = "http://#{ENV["SERVER_ADDRESS"]}:8080"
  @http_client = HTTPClient.new
end

Given "I have a user account" do
  @user = "rbetts"
  @password = "passw0rd"
end

Then "I should be able to login to the user account" do
  url = "#{@server_address}/user/#{@user}"
    response = @http_client.get(url)
    assert_equal(response.status, HTTP::Status::OK, "Error logging in user #{url}")
end

Then "Then I should be able to get a stock quote for 'IBM' with the value '1000000.00'" do
	url = "#{@server_address}/quote/IBM"
	response = @http_client.get(url)
	assert_equal(response.status, HTTP::Status::OK, "Error requesting stock quote for 'IBM'")
	assert(response.body == "1000000.00", "Value of stock quote for 'IBM' incorrect, expected '1000000.00' but found #{response.body}")
end
