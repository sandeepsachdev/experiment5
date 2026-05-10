package com.example.demo.service;

import com.example.demo.model.Person;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    private static final List<Person> DIRECTORY = List.of(
        new Person("1",  "Alice Johnson",    "United States", "Software Engineer",        "Acme Corp",        "alice.johnson@acme.com"),
        new Person("2",  "Bob Smith",         "United Kingdom","Product Manager",          "TechVentures Ltd", "bob.smith@techventures.co.uk"),
        new Person("3",  "Carlos Rivera",     "Mexico",        "Data Scientist",           "DataWave",         "c.rivera@datawave.mx"),
        new Person("4",  "Diana Chen",        "Canada",        "UX Designer",              "PixelCraft",       "diana.chen@pixelcraft.ca"),
        new Person("5",  "Ethan Müller",      "Germany",       "DevOps Engineer",          "CloudNine GmbH",   "ethan.mueller@cloudnine.de"),
        new Person("6",  "Fatima Al-Rashid",  "United Arab Emirates", "Solutions Architect", "InfraStack",    "f.alrashid@infrastack.ae"),
        new Person("7",  "Grace Park",        "South Korea",   "Software Engineer",        "SeoulTech",        "grace.park@seoultech.kr"),
        new Person("8",  "Hiroshi Tanaka",    "Japan",         "Machine Learning Engineer","Robotix",          "h.tanaka@robotix.jp"),
        new Person("9",  "Isabel Santos",     "Brazil",        "Product Manager",          "BravoApps",        "i.santos@bravoapps.com.br"),
        new Person("10", "James O'Brien",     "Ireland",       "Full Stack Developer",     "GreenCode",        "james.obrien@greencode.ie"),
        new Person("11", "Kavya Nair",        "India",         "Data Scientist",           "AnalytIQ",         "kavya.nair@analytiq.in"),
        new Person("12", "Liam Thompson",     "Australia",     "Cloud Architect",          "SkyBridge",        "liam.thompson@skybridge.com.au"),
        new Person("13", "Maria Rossi",       "Italy",         "UX Designer",              "Studio Forma",     "m.rossi@studioforma.it"),
        new Person("14", "Nathan Williams",   "United States", "DevOps Engineer",          "Acme Corp",        "n.williams@acme.com"),
        new Person("15", "Olivia Brown",      "United Kingdom","Solutions Architect",      "TechVentures Ltd", "o.brown@techventures.co.uk"),
        new Person("16", "Pierre Dubois",     "France",        "Software Engineer",        "CodeParis",        "p.dubois@codeparis.fr"),
        new Person("17", "Qian Liu",          "China",         "Machine Learning Engineer","AIStar",           "qian.liu@aistar.cn"),
        new Person("18", "Rosa Martinez",     "Spain",         "Product Manager",          "IberApps",         "r.martinez@iberapps.es"),
        new Person("19", "Samuel Osei",       "Ghana",         "Full Stack Developer",     "AfriTech",         "s.osei@afritech.gh"),
        new Person("20", "Tanya Ivanova",     "Russia",        "Data Scientist",           "DataBridge",       "t.ivanova@databridge.ru"),
        new Person("21", "Uma Patel",         "India",         "Software Engineer",        "DevIndia",         "uma.patel@devindia.in"),
        new Person("22", "Viktor Novak",      "Czech Republic","Cloud Architect",          "EuroCloud",        "v.novak@eurocloud.cz"),
        new Person("23", "Wendy Zhang",       "Canada",        "UX Designer",              "PixelCraft",       "wendy.zhang@pixelcraft.ca"),
        new Person("24", "Xander Bakker",     "Netherlands",   "DevOps Engineer",          "DutchOps",         "x.bakker@dutchops.nl"),
        new Person("25", "Yuki Kobayashi",    "Japan",         "Product Manager",          "Robotix",          "y.kobayashi@robotix.jp")
    );

    public List<Person> search(String name, String country, String jobTitle) {
        String n  = normalise(name);
        String c  = normalise(country);
        String jt = normalise(jobTitle);

        return DIRECTORY.stream()
                .filter(p -> n.isEmpty()  || normalise(p.getName()).contains(n))
                .filter(p -> c.isEmpty()  || normalise(p.getCountry()).contains(c))
                .filter(p -> jt.isEmpty() || normalise(p.getJobTitle()).contains(jt))
                .toList();
    }

    private String normalise(String value) {
        return value == null ? "" : value.trim().toLowerCase();
    }
}
