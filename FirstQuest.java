import java.util.Random;
import java.util.Arrays;
class FirstQuest{

	static String Factor(int num){

		if(num<0) {
			return "Error: negative number.";
		}
		if(num==0) return "1";
		int result = num*Integer.parseInt(Factor(num-1));
		return String.valueOf(result);
	}
	static String Power(int num, int aver){
		double result=1;
		if(aver!=0){
			if (aver<0){
				aver*=-1;
				for(;aver>0;aver--){
					result*=num;
				}
				return String.valueOf(1/result);
			}
			else {
				for(;aver>0;aver--){
				result*=num;
				}
				return String.valueOf(result);
			}
		}else if (num!=0)return "1";
				else return "0";
	}

	static void Sort(int[] arr){
		if(arr != null) {
			for (int i = arr.length - 1; i >= 0; i--) {
				for (int j = 0; j < i; j++) {
					if (arr[j] > arr[j + 1]) {
						int t = arr[j];
						arr[j] = arr[j + 1];
						arr[j + 1] = t;
					}
				}
			}
		}
	}
	private static Random random = new Random();
	static int[] Generate(int size) {
		if (size >= 0) {
			int[] Arr = new int[size];
			for (int i = 0; i < size; i++) {
				Arr[i] = Math.abs(random.nextInt(20));
			}
			return Arr;
		}
		else
			return null;
	}
	public static void main(String[] args){
		System.out.println("Zdravstvui, Mir!");
		try {
			int num = Integer.parseInt(args[0]);
			int aver = Integer.parseInt(args[1]);
		System.out.println("Step: "+Power(num,aver));
		System.out.println("Factor: "+Factor(num));
		int[] arr = Generate(num);
		System.out.println("Before Sort: " + Arrays.toString(arr));
		Sort(arr);
		System.out.println("After Sort: " + Arrays.toString(arr));
		}catch (NumberFormatException e)
		{
			System.out.println("Error: Wrong data (number was exepted).");
			return;
		}

	}
}