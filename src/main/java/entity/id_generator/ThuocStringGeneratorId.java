package main.java.entity.id_generator;

import java.io.Serializable;
import java.util.stream.Stream;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.query.spi.QueryImplementor;

public class ThuocStringGeneratorId implements IdentifierGenerator {

		private String prefix = "Thuoc";

		@Override
		public Serializable generate(SharedSessionContractImplementor session, Object object) {
			// Select all id
			QueryImplementor<String> query = session.createQuery("SELECT thuoc.maThuoc FROM Thuoc thuoc", String.class);
			try (Stream<String> stream = query.stream()) {
				Long max = stream.map(t -> t.replace(prefix, ""))
						.mapToLong(Long::parseLong)
						.max()
						.orElse(0L);
				return prefix + String.format("%08d", max + 1);
			}
		}
	}