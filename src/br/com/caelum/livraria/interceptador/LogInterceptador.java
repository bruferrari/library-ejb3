package br.com.caelum.livraria.interceptador;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

public class LogInterceptador {

	@AroundInvoke
	public Object interceptor(InvocationContext context) throws Exception {
		long millis = System.currentTimeMillis();

		//chamada do método dao
		Object o = context.proceed();
		
		String metodo = context.getMethod().getName();
		String nomeClasse = context.getTarget().getClass().getSimpleName();

		System.out.println(nomeClasse + ".class, " + metodo + "()");
		System.out.println("[INFO]: Tempo gasto: " + (System.currentTimeMillis() - millis));
		
		return o;
	}
}
