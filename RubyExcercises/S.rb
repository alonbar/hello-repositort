#Method visit. will print the file name according to the parameters.
def visit(path, indent, mode)
  str = "  " * indent
  str = str + path
  if mode == "n" || mode == "t"
    str = str + " " +  calcDirSize(path).to_s
  end
  puts str
end

#Calculating a folder over a windows OS.
def calcDirSize(path)
  if !File.directory?(path)
    return File.size(path)
  end
  list = Dir.entries(path);
  sizeToReturn = 0
  for index in 0 ... list.size
    if list[index] != '.' && list[index] != '..'
      if File.directory?(path + "\\" + list[index])
        sizeToReturn +=  calcDirSize (path + "\\" + list[index])
      else
        sizeToReturn += File.size(path + "\\" + list[index])
      end
    end
  end
  return sizeToReturn
end

