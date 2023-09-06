import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DemoOptional {

	List<Integer> getList() {

		return Arrays.asList(1, 2, 3, 4, 5, 6, 7);

	}
	
	public static void main(String[] args) {
		DemoOptional d = new DemoOptional();

		Optional.of(d.getList())
					.filter(l -> l.size() > 0)
					.ifPresent(l -> System.out.println(l.get(0)));
		
		Optional.of(d.getList())
					.filter(l -> l.size() > 0)
					.ifPresentOrElse(l -> System.out.println(l.get(0)), () -> System.out.println("Esta vacia"));		
		
		System.out.println(Optional.of(d.getList())
					 			   .filter(l -> !l.isEmpty())
					               .map(l -> l.get(0))
					               .orElseGet(() -> -1));

		Optional<Integer> opt = Optional.of(1);
		
		opt.stream().forEach(x -> System.out.println("Elemento -> " + x));
		
		System.out.println(Optional.ofNullable(d.getList())
	 			   .filter(l -> !l.isEmpty())
	               .map(l -> l.get(0))
	               .orElseThrow(() -> new RuntimeException("La lista esta vacia")));

		
		System.out.println("--------------");
		List<Integer> lIntMayCuatro = Optional.of(d.getList())
										.filter(l -> l.size() > 0)
										.map(l -> l.stream()
												.filter(n -> n >= 4)
												.collect(Collectors.toList()))
										.map(l -> l.stream()
												.map(n -> n * 10)
												.map(n -> n - 1)
												.collect(Collectors.toList()))
										.orElseGet(ArrayList::new);
		
		lIntMayCuatro.stream().forEach(System.out::println);
	}

}
