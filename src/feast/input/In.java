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
 * Some of this code is a bit hacky.  This is because in spite of maintaining
 * the illusion of modifying a persistent Input/In object, we're actually
 * creating new an entirely new object every time a default value is set or
 * a rule is changed.
 *
 * @author Tim Vaughan <tgvaughan@gmail.com>
 * @param <T> Type of input
 */
public class In<T> extends beast.core.Input<T> {

    /**
     * Fields we need because we can't access them in Input.
     */
    final private String name, tipText;
    final private T myDefault;
    
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
        this.myDefault = null;
    }
    
    /**
     * Private constructor for optional input with a start value.
     * 
     * @param name         name of input
     * @param tipText      tip text for BEAUti
     * @param defaultValue default value for input
     */
    private In(In<T> prevIn, T defaultValue) {
        super(prevIn.name, prevIn.tipText, defaultValue);
        this.name = prevIn.name;
        this.tipText = prevIn.tipText;
        this.myDefault = defaultValue;
    }

    /**
     * Private constructor for inputs with an XOR validation rule.
     * 
     * @param name         name of input
     * @param tipText      tip text for BEAUti
     * @param defaultValue default value for input
     * @param other        other input
     */
    private In(In<T> prevIn, beast.core.Input other) {
        super(prevIn.name, prevIn.tipText, prevIn.myDefault, Validate.XOR, other);
        this.name = prevIn.name;
        this.tipText = prevIn.tipText;
        this.myDefault = prevIn.myDefault;
    }
    
    /**
     * Assign default value.  Returns input to allow for method chaining.
     * 
     * @param value
     * @return this
     */
    public In<T> setDefault(T value) {
        return new In(this, value);
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
     * Set an XOR validation rule with other.  Returns input to allow for
     * method chaining.
     * 
     * @param other input with which to create XOR validation rule
     * @return this
     */
    public In<T> setXOR(beast.core.Input other) {
        return new In(this, other);
    }
    

    // Static methods

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
}
