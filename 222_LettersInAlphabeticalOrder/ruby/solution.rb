
#Returns true if all adjacent elements in the passed string return true from the given block
def in_some_order(str)
  results = []
  str.split("").each_cons(2) { |a| results << yield(a[0],a[1]) }
  results.all? {|a| a == true }
end

if __FILE__ == $0 
  num_test_cases = gets.chomp.to_i

  num_test_cases.times do |i|
    cur_word = gets.chomp

    if in_some_order(cur_word) {|a,b| a <= b}
      puts cur_word + " IN ORDER"
    elsif in_some_order(cur_word) {|a,b| a >= b}
      puts cur_word + " IN REVERSE ORDER"
    else 
      puts cur_word + " NOT IN ORDER"
    end
  end
end
