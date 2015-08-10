load 'Task.rb'
def initServer
  require 'sinatra'
  require 'prime'
  get '/' do
    'This is Ruby web excercise!'
  end

  get '/task=get_time' do
    # matches "GET /hello/foo" and "GET /hello/bar"
    # params['name'] is 'foo' or 'bar'
    task = GetTimeTask.new
    "Current Time : #{task.action}"
  end

  get '/task=is_prime' do
    # matches "GET /posts?title=foo&author=bar"
    task = IsPrimeTask.new(params['x'])
    "is it prime #{task.action}"
    # uses title and author variables; query is optional to the /posts route
  end

  # get '/task=is_prime' do
  #   # matches "GET /posts?title=foo&author=bar"
  #   x = params['x']
  #   # uses title and author variables; query is optional to the /posts route
  #   'x'
  # end
end

initServer