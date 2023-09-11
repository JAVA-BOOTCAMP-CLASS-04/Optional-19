import java.util.*;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.DoubleBinaryOperator;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DemoOptional {

	List<Integer> getList() {

		return Arrays.asList(1, 2, 3, 4, 5, 6, 7);

	}

	public Double suma(Double s1, Double s2) {
		if (s1 == null) throw new RuntimeException("Uno de los sumandos es nulo");

		if (s2 == null) throw new RuntimeException("Uno de los sumandos es nulo");

		return s1 + s2;
	}

	public Double sumaFunctional(Double s1, Double s2) {
		DoubleBinaryOperator add = Double::sum;

		return Optional.ofNullable(s1)
				.map(d1 -> Optional.ofNullable(s2)
						.map(d2 -> add.applyAsDouble(d1, d2))
						.orElseThrow(() -> new RuntimeException("Uno de los sumandos es nulo")))
				.orElseThrow(() -> new RuntimeException("Uno de los sumandos es nulo"));
	}


	public Double sumaFunctional2(Double s1, Double s2) {
		DoubleBinaryOperator add = Double::sum;

		return Optional.ofNullable(s1)
				.flatMap(d1 -> Optional.ofNullable(s2)
						.map(d2 -> add.applyAsDouble(d1, d2)))
				.orElseThrow(() -> new RuntimeException("Uno de los sumandos es nulo"));
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


		System.out.println("--------------");

		System.out.println("Suma (10, 20) -> " + d.suma(10.0, 20.0));

		System.out.println("Suma FUNC (10, 20) -> " + d.sumaFunctional(10.0, 20.0));

		System.out.println("Suma FUNC (10, 20) -> " + d.sumaFunctional2(10.0, 20.0));
	}

}
