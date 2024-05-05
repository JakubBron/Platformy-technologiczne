using System;
using System.Collections.Generic;
using System.Text;
using System.Xml.Serialization;

namespace Lab9
{
    [XmlType("car")]           // changes in XML phrase "Car" to "car"
    public class Car
    {
        public string model;
        public int year;
        [XmlElement(ElementName = "engine")]
        public Engine motor { get; set; } // getters & setters will be avaliable without need to implement it

        public Car()
        {
            
        }

        public Car(string modelName, Engine motorOnBoard, int yearOfProduction)
        {
            model = modelName;
            year = yearOfProduction;
            motor = motorOnBoard;
        }

        public void setModel(string modelName)
        {
            model = modelName;
        }
        
        public string getModel()
        {
            return model;
        }

        public void setYear(int yearOfProduction)
        {
            year = yearOfProduction;
        }

        public int getYear()
        {
            return year;
        }
    }
}
