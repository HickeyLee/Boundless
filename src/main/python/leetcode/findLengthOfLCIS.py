
import numbers
from pip import main


def findLengthOfLCIS(nums):
    mmax_len_list = []
    max_len = 1
    mmax_len_list.append(max_len)
    mmax_len_list.append(max_len)
    for i in range(len(nums) - 1):
        if nums[i + 1] > nums[i] :
            max_len = max_len + 1
        else:
            mmax_len_list.append(max_len)
            max_len = 1

    mmax_len_list.append(max_len)
    return max(mmax_len_list)
    


def findMostLongsubList(nums):
    lenth = 1
    res = []
    
    def findStartNode(subnums):
        res = [subnums[0]]
        if len(subnums) == 1:
            res = subnums
        else :
            for i in range(1, len(subnums)):
                if subnums[i] > res[-1]:
                    res.append(subnums[i])
                else:
                    pass
            
    for i in range(0, len(nums)):#[0,1,2,3]
        v = nums[0: i + 1]
        node = findStartNode(v)
        print(i, node)
        if len(node) > length:
            length = len(node)
            res = node 
        else:
            pass

            
            

def main(): 
    numbers = [1,3,5,4]
    val = findMostLongsubList(numbers)
    print(val) 
    
    
    

if __name__ == "__main__":
    main()

 

