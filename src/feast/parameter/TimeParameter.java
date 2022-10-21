package feast.parameter;

import beast.base.core.Description;
import beast.base.core.Input;
import beast.base.core.Log;
import beast.base.inference.parameter.RealParameter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Description("RealParameter with a constructor that sets initial values based " +
        "on formatted dates converted to ages relative to the most recent sample " +
        "on the tree.")
public class TimeParameter extends RealParameter {

    public Input<String> timeFormatInput = new Input<>("timeFormat",
            "The time format to be parsed, e.g. dd/M/yyyy");

    public Input<String> timeInput = new Input<>("time",
            "One or more (space-delimited) times to be used in initializing parameter",
            Input.Validate.REQUIRED);

    public Input<String> mostRecentSampleTimeInput = new Input<>("mostRecentSampleTime",
            "Time of the most recent sample, in format specified by timeFormat",
            Input.Validate.REQUIRED);

    public TimeParameter() {
        valuesInput.setRule(Input.Validate.OPTIONAL);
    }

    @Override
    public void initAndValidate() {
        Double offsetTime = getTimeAsDouble(mostRecentSampleTimeInput.get());

        for (String timeString : timeInput.get().split(" "))
            valuesInput.setValue(offsetTime - getTimeAsDouble(timeString), this);

        super.initAndValidate();
    }

    private double getTimeAsDouble(String timeString) {
        double thisValue;

         try {
            if (timeFormatInput.get() != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(timeFormatInput.get());
                LocalDate date = LocalDate.parse(timeString, formatter);

                Log.warning.println("Using format '" + timeFormatInput.get() + "' to parse '" + timeString +
                        "' as: " + (date.getYear() + (date.getDayOfYear() - 1.0) / (date.isLeapYear() ? 366.0 : 365.0)));

                thisValue = date.getYear() + (date.getDayOfYear() - 1.0) / (date.isLeapYear() ? 366.0 : 365.0);
            } else {
                thisValue = Double.parseDouble(timeInput.get());
            }
        } catch (NumberFormatException | DateTimeParseException ex) {
            throw new IllegalArgumentException("Error parsing input to TimeParameter - " +
                    "must be either a number or a properly formatted date string.");
        }

        return thisValue;
    }
}
