/*
 * Copyright (C) 2014 Tim Vaughan <tgvaughan@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package feast.input;


/**
 * An extension of BEAST's Input class that allows for some simpler idioms.
 *
 * @author Tim Vaughan <tgvaughan@gmail.com>
 * @param <T> Type of input
 */
public class In<T> extends beast.core.Input<T> {

    String name, tipText;
    
    /**
     * Construct a new input with the chosen name and tip text.
     * 
     * @param name    name of input
     * @param tipText tip text for BEAUti
     */
    public In(String name, String tipText) {
        super(name, tipText);
        this.name = name;
        this.tipText = tipText;
    }
    
    /**
     * Private constructor for inputs with an XOR validation rule.
     * 
     * @param name    name of input
     * @param tipText tip text for BEAUti
     * @param other   Other input
     */
    private In(String name, String tipText, beast.core.Input other) {
        super(name, tipText, Validate.XOR, other);
        this.name = name;
        this.tipText = tipText;
    }
    
    /**
     * Assign default value.  Returns input to allow for method chaining.
     * 
     * @param value
     * @return this
     */
    public In<T> setDefault(T value) {        
        this.defaultValue = value;
        return this;
    }
    
    /**
     * Static method for setting the default value of an existing Input.
     * 
     * @param <T>
     * @param input Input instance
     * @param value new default value
     * @return Input instance (maybe no good reason?)
     */
    public static <T> beast.core.Input<T> setDefault(beast.core.Input<T> input, T value) {
        input.defaultValue = value;
        return input;
    }
    
    /**
     * Set the input to be required. Returns input to allow for method chaining.
     * @return this
     */
    public In<T> setRequired() {
        setRule(Validate.REQUIRED);
        return this;
    }
    
    /**
     * Static method for making existing input required.
     * 
     * @param <T>
     * @param input Input instance
     * @return Input instance (maybe no good reason?)
     */
    public static <T> beast.core.Input<T> setRequired(beast.core.Input<T> input) {
        input.setRule(Validate.REQUIRED);
        return input;
    }
    
    /**
     * Static method for making existing input optional.
     * 
     * @param <T>
     * @param input Input instance
     * @return Input instance (maybe no good reason?)
     */
    public static <T> beast.core.Input<T> setOptional(beast.core.Input<T> input) {
        input.setRule(Validate.OPTIONAL);
        return input;
    }
    
    /**
     * Set an XOR validation rule with other.  Returns input to allow for
     * method chaining.
     * 
     * @param other input with which to create XOR validation rule
     * @return this
     */
    public In<T> setXOR(beast.core.Input other) {
        // This is a bit hacky due to Input.other being package private.
        return new In(name, tipText, other);
    }
    
    /**
     * Static factory method for creating inputs.  The advantage over the
     * standard constructor is that here we get to use type inference, even
     * in Java 6.  The disadvantage is that we can't use this at the
     * start of a method chain due to the limitations of this inference.
     * 
     * @param <T> The type.  Note that this is not required.
     * @param name    the input name
     * @param tipText the tip text for BEAUti
     * @return a new input object
     */
    public static <T> In<T> create(String name, String tipText) {
        return new In<T>(name, tipText);
    }
}
