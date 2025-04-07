package pl.financemanagement.BankAccount.Model;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import pl.financemanagement.BankAccount.Model.Exceptions.CurrencyException;

import java.io.IOException;

public class CurrencyDeserializer extends StdDeserializer<Currency> {

    public CurrencyDeserializer() {
        super(Currency.class);
    }

    @Override
    public Currency deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        String value = p.getText().toUpperCase();
        try {
            return Currency.valueOf(value);
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }
}
