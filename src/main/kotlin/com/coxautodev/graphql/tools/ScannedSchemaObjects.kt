package com.coxautodev.graphql.tools

import com.google.common.collect.BiMap
import graphql.language.DirectiveDefinition
import graphql.language.FieldDefinition
import graphql.language.ObjectTypeDefinition
import graphql.language.TypeDefinition
import graphql.schema.GraphQLScalarType

/**
 * @author Andrew Potter
 */
internal data class ScannedSchemaObjects(
        val dictionary: TypeClassDictionary,
        val definitions: Set<TypeDefinition<*>>,
        val customScalars: CustomScalarMap,
        val directiveDefinitions: Set<DirectiveDefinition>,
        val rootInfo: RootTypeInfo,
        val fieldResolversByType: Map<ObjectTypeDefinition, MutableMap<FieldDefinition, FieldResolver>>,
        val unusedDefinitions: Set<TypeDefinition<*>>
)

internal typealias TypeClassDictionary = BiMap<TypeDefinition<*>, JavaType>
internal typealias CustomScalarMap = Map<String, GraphQLScalarType>
