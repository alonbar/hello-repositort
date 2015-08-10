load 'S.rb'

#This Method is recurde though a folder and according to the paramters with send files/directories to method visit.
def walk(path, indent, mode, indentMax)
 list = Dir.entries(path);
 if (mode == "t")
   list.delete(".")
   list.delete("..")
   list.sort! { |a,b| calcDirSize(path + "\\" + b) <=> calcDirSize(path + "\\" + a)}
 end
 for index in 0 ... list.size
   if list[index] != '.' && list[index] != '..'
     str = path + list[index]
     if File.directory?(path + "\\" + list[index]) && (mode == "t" || mode == "n")
       visit(path + "\\" + list[index], indent, mode)
       if mode != "n" || indentMax == -1 || indent < indentMax
         walk(path + "\\" + list[index], indent + 1, mode, indentMax)
       end
     elsif mode == "d"
       visit(path + "\\" + list[index], indent, mode)
     end
   end
 end
end

#Entry point. parsing the arguements.
if __FILE__ == $0
  argsMessage = "Usage: traverse.tb [path]"
  args = "C:\\temp\\walkFolder"
  mode = "t"
  # walk(args, 0, mode, -1)
  walk(args, 0, "t", -1)
  if ARGV.size == 0
    puts argsMessage
  elsif File.exist?(ARGV[0]) == false
    puts "file does not exist"
  else
    if (File.file?(ARGV[0]) == true)
      puts "#{ARGV[0]} is a file not a directory"
    else
      if (ARGV.size == 1)
        walk(ARGV[0], 0, "d", -1)
      elsif (ARGV.size == 2 && ARGV[1] == "n")
        puts "option 2"
        walk(ARGV[0], 0, "n", -1)
      elsif (ARGV.size == 2 && ARGV[1] == "t")
        puts "option 4"
        walk(ARGV[0], 0, "t", -1)
      elsif (ARGV.size == 3 && ARGV[1] == "n")
        puts Integer(ARGV[2])
        walk(ARGV[0], 0, "n", Integer(ARGV[2]))
      end
    end
  end
end

