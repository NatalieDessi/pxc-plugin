package de.natalie.pxc.model;

import com.fasterxml.jackson.core.type.TypeReference;

import java.util.Map;
import java.util.UUID;

public class HomeTypeReference extends TypeReference<Map<UUID, HomeLocation>> { }
