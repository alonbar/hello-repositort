require('prime')
class Task
  def initialize(id)
    @taskID=id
  end

  def action
    return 'This is a generic task'
  end

  def getTaskID
    return @taskID
  end

end

class GetTimeTask < Task
  def initialize
    super("time")
  end

  def action
    time1 = Time.new
    return time1.to_s
  end

end


class IsPrimeTask < Task
  def initialize(x)
    super("prime")
    @x = x
  end

  def action
    res = Prime.prime?(Integer(@x))
    return res.to_s
  end

end