import java.util.Arrays;
import java.util.List;
import org.jetbrains.annotations.Contract;

public class IfStatementMissingBreakInLoop {

  public static <T> void assertOneOf(T value, T... values) {
    boolean found = false;
    for (T v : values) {
      <warning descr="Loop can be terminated after condition is met">if</warning> (value != null && value.equals(v)) {
        found = true;
      }
    }
    assert found : "Value not found in " + Arrays.toString(values);
  }

  public static void loops(String[] arr) {
    boolean flag = false;
    for (int i = 0; i < arr.length; i++) {
      <warning descr="Loop can be terminated after condition is met">if</warning> (arr[i] == null) {
        flag = true;
      }
    }

    for (String s : arr) {
      <warning descr="Loop can be terminated after condition is met">if</warning> (s == null) {
        flag = true;
      }
    }

    int i = 0;
    while (i < arr.length) {
      <warning descr="Loop can be terminated after condition is met">if</warning> (arr[i] == null) {
        flag = true;
      }
      i++;
    }

    i = 0;
    do {
      <warning descr="Loop can be terminated after condition is met">if</warning> (arr[i] == null) {
        flag = true;
      }
      i++;
    } while (i != arr.length);
  }

  public void callPureMethod(String[] arr) {
    boolean flag = false;
    for (String s : arr) {
      boolean b = isEmpty(s);
      <warning descr="Loop can be terminated after condition is met">if</warning> (b) {
        flag = true;
      }
    }
  }

  public void innerVariableChange(String[] arr, int expectedCnt) {
    boolean tooLong = false;
    for (String s : arr) {
      int cnt = s.length();
      cnt += arr.length;
      <warning descr="Loop can be terminated after condition is met">if</warning> (cnt > expectedCnt) {
        tooLong = true;
      }
    }
  }

  public void multipleAssignments(String[] arr) {
    boolean flag1 = false;
    boolean flag2 = true;
    for (String s : arr) {
      <warning descr="Loop can be terminated after condition is met">if</warning> (s == null) {
        flag1 = false;
        flag2 = false;
      }
    }
  }

  public void multipleDependentAsignments(String[] arr) {
    boolean flag1 = false;
    boolean flag2 = true;
    for (String s : arr) {
      if (s == null) {
        flag1 = flag2;
        flag2 = flag1;
      }
    }
  }

  public void assignValueThatIsConstantDuringIterations(String[] arr) {
    String foo = "foo";
    String res = "";
    for (String s : arr) {
      String three = "3";
      <warning descr="Loop can be terminated after condition is met">if</warning> (s == null) {
        res = foo + three;
      }
    }
    foo = "bar";
  }

  public void localOnlySideEffects(String[] array, List<Integer> list) {
    boolean found = false;
    for(int i = 0; i < 10; i++) {
      String s = array[i];
      int len = list.get(i);
      len++;
      <warning descr="Loop can be terminated after condition is met">if</warning>(s.length() == len) {
        found = true;
      }
    }
  }

  static class Nested {

    private final String field = "foo";

    void assignField(String[] arr) {
      boolean flag = false;
      for (String s : arr) {
        String f = this.field;
        <warning descr="Loop can be terminated after condition is met">if</warning> (s == null) {
          flag = true;
        }
      }
    }

  }

  @Contract(pure = true)
  public boolean isEmpty(String s) {
    return s == null || "".equals(s);
  }
}